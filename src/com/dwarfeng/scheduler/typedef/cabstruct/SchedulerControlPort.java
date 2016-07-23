package com.dwarfeng.scheduler.typedef.cabstruct;

import java.util.Queue;

import com.dwarfeng.dwarffunction.threads.NadeRunner;

public interface SchedulerControlPort {

	/**
	 * 向该程序的后台中添加一个运行器。
	 * @param runner 指定的运行器。
	 */
	public void backgroundInvoke(NadeRunner runner);
	
	/**
	 * 获取程序后台的等待队列的元素数量。
	 * @return 元素数量。
	 */
	public int getBackgroundQueueSize();
	
	/**
	 * 获取后台等待队列中的所有元素。
	 * <p> 元素以队列的形式返回，这个队列是一个副本，更改此队列不会对
	 * 后台中原有的队列造成影响。
	 * @return 后台等待队列中所有元素组成的新队列。
	 */
	public Queue<NadeRunner> getBackgroundQueue();
	
}
