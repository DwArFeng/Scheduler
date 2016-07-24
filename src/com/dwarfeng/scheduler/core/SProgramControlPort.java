package com.dwarfeng.scheduler.core;

import java.util.Locale;
import java.util.Queue;

import com.dwarfeng.dwarffunction.program.mvc.ProgramControlPort;
import com.dwarfeng.dwarffunction.threads.NadeRunner;

public interface SProgramControlPort extends ProgramControlPort{

	/**
	 * 向程序的后台队列中增加一个新的运行对象。
	 * @param runner 运行对象。
	 */
	public void backgroundInvoke(NadeRunner runner);
	
	/**
	 * 返回程序的后台队列的正在等待的元素数量。
	 * @return 后台队列正在等待的元素数量。
	 */
	public int getBackgroundQueueSize();
	
	/**
	 * 返回程序后台队列的正在等待的元素组成的队列。
	 * <p> 该队列是后台队列的副本，即使对该队列进行修改，也不会对程序后台的原始队列造成影响。
	 * @return 程序后台队列中的所有元素组成的心队列。
	 */
	public Queue<NadeRunner> getBackgroundQueue();
	
	
	
	/**
	 * 设置程序的语言地区。
	 * <p> 文本字段会自动变更为指定地区对应的字段。
	 * @param locale 指定的地区。
	 */
	public void setLocale(Locale locale);

	
}
