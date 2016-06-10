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
 * 维持程序运行的工厂类，该类可以向其中添加{@linkplain Runnable}，并以一个独立的线程
 * 按照{@linkplain Runnable} 添加的先后顺序执行其中的方法。
 * @author DwArFeng
 * @since 1.8
 */
public class RunnerQueue {
	
	/**线程的名称*/
	private final static String threadName = "RunnerQueue"; 
	/**是否正在运行的标记*/
	private static boolean runFlag;
	/**Runnable 队列*/
	private static Queue<Runnable> queue;
	/**线程*/
	private static Thread runnerThread;
	/**线程同步锁*/
	private static Lock threadLock;
	/**线程状态*/
	private static Condition threadCondition;
	/**队列读写锁*/
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
				CT.trace("维持线程已经被关闭");
			}
		},threadName);
	}
	
	/**
	 * 检查队列中还有没有更多的Runnable
	 * @return 有没有更多的Runnable
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
	 * 获取并移除位于队列首位的Runnable。
	 * @return 队列首位的Runnable。
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
	 * 向队列的末尾添加指定的Runnable。
	 * @param runnable 指定的Runnable。
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
	 * 向维护队列中添加一个新的Runnable。
	 * @param runnable 指定的Runnable。
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
	
	//静态初始化方法
	static{
		queue = new ArrayDeque<Runnable>();
		threadLock = new ReentrantLock();
		threadCondition = threadLock.newCondition();
		queueLock = new ReentrantReadWriteLock();
		genThread();
		runnerThread.start();
	}
}
