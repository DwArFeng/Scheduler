package com.dwarfeng.scheduler.typedef.cabstruct;

import java.util.Queue;

import com.dwarfeng.dwarffunction.threads.NadeRunner;

public interface SchedulerControlPort {

	/**
	 * ��ó���ĺ�̨�����һ����������
	 * @param runner ָ������������
	 */
	public void backgroundInvoke(NadeRunner runner);
	
	/**
	 * ��ȡ�����̨�ĵȴ����е�Ԫ��������
	 * @return Ԫ��������
	 */
	public int getBackgroundQueueSize();
	
	/**
	 * ��ȡ��̨�ȴ������е�����Ԫ�ء�
	 * <p> Ԫ���Զ��е���ʽ���أ����������һ�����������Ĵ˶��в����
	 * ��̨��ԭ�еĶ������Ӱ�졣
	 * @return ��̨�ȴ�����������Ԫ����ɵ��¶��С�
	 */
	public Queue<NadeRunner> getBackgroundQueue();
	
}
