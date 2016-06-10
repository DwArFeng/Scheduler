package com.dwarfeng.scheduler.core;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.func.io.CT;

/**
 * ά�ֳ������еĹ����࣬����������������{@linkplain Runnable}������һ���������߳�
 * ����{@linkplain Runnable} ��ӵ��Ⱥ�˳��ִ�����еķ�����
 * @author DwArFeng
 * @since 1.8
 */
public class RunnerQueue {
	
	/**�̵߳�����*/
	private final static String threadName = "RunnerQueue"; 
	/**�Ƿ��������еı��*/
	private static boolean runFlag;
	/**Runnable ����*/
	private static Queue<Runnable> queue;
	/**�߳�*/
	private static Thread runnerThread;
	/**�߳�ͬ����*/
	private static Lock threadLock;
	/**�߳�״̬*/
	private static Condition threadCondition;
	/**���ж�д��*/
	private static ReadWriteLock queueLock;
	
	public RunnerQueue(){}
	
	private static void genThread(){
		if(runnerThread != null && runnerThread.isAlive()){
			runFlag = false;
			runnerThread.interrupt();
			while(runnerThread.isAlive());
		}
		runFlag = true;
		runnerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(runFlag){
					threadLock.lock();
					try{
						while(!hasMoreRunnable()){
							threadCondition.await();
						}
						Runnable runnable = peek();
						try{
							runnable.run();
						}catch(Exception e){
							e.printStackTrace();
						}
						poll();
					}catch(InterruptedException e){
						//do nothing
					}finally{
						threadLock.unlock();
					}
				}
				CT.trace("ά���߳��Ѿ����ر�");
			}
		},threadName);
	}
	
	/**
	 * �������л���û�и����Runnable
	 * @return ��û�и����Runnable
	 */
	private static boolean hasMoreRunnable(){
		queueLock.readLock().lock();
		try{
			return queue.size() > 0;
		}finally{
			queueLock.readLock().unlock();
		}
	}
	
	/**
	 * ��ȡ���Ƴ�λ�ڶ�����λ��Runnable��
	 * @return ������λ��Runnable��
	 */
	private static Runnable poll(){
		queueLock.writeLock().lock();
		try{
			return queue.poll();
		}finally{
			queueLock.writeLock().unlock();
		}
	}
	
	private static int getQueueSize(){
		queueLock.readLock().lock();
		try{
			return queue.size();
		}finally{
			queueLock.readLock().unlock();
		}
	}
	/**
	 * ����е�ĩβ���ָ����Runnable��
	 * @param runnable ָ����Runnable��
	 */
	private static void offer(Runnable runnable){
		queueLock.writeLock().lock();
		try{
			queue.offer(runnable);
		}finally{
			queueLock.writeLock().unlock();
		}
	}
	
	/**
	 * ��ά�����������һ���µ�Runnable��
	 * @param runnable ָ����Runnable��
	 */
	public static void invoke(Runnable runnable){
		boolean flag = getQueueSize() == 0;
		offer(runnable);
		if(flag){
			threadLock.lock();
			try{
				threadCondition.signalAll();
			}finally{
				threadLock.unlock();
			}
		}
	}
	private static Runnable peek(){
		queueLock.readLock().lock();
		try{
			return queue.peek();
		}finally{
			queueLock.readLock().unlock();
		}
	}
	
	//��̬��ʼ������
	static{
		queue = new ArrayDeque<Runnable>();
		threadLock = new ReentrantLock();
		threadCondition = threadLock.newCondition();
		queueLock = new ReentrantReadWriteLock();
		genThread();
		runnerThread.start();
	}
}
