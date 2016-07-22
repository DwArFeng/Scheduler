package com.dwarfeng.scheduler.core;

import java.util.MissingResourceException;

import com.dwarfeng.dwarffunction.threads.NadeRunner;
import com.dwarfeng.dwarffunction.threads.RunnerQueue;
import com.dwarfeng.scheduler.info.ExceptionStringFieldKey;
import com.dwarfeng.scheduler.info.LabelStringFieldKey;
import com.dwarfeng.scheduler.info.MessageStringFieldKey;
import com.dwarfeng.scheduler.typedef.cabstruct.SControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SViewMgr;

/**
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p> 程序结构采用MVC结构，并且尽可能的做到相互分离。
 * <br> <code>Scheduler</code>本身拥有一个后台维护器，任何需要调用后台的方法都可以托管到这个维护器上。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler {

	/**有关的后台*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	
//	/**
//	 * 向该程序的后台中添加一个运行器。
//	 * @param runner 指定的运行器。
//	 */
//	public static void backgroundInvoke(NadeRunner runner){
//		background.invoke(runner);
//	}
//	
//	/**
//	 * 获取程序后台的等待队列的元素数量。
//	 * @return 元素数量。
//	 */
//	public static int getBackgroundQueueSize(){
//		return background.getQueueSize();
//	}
	
	/**有关的文本字段*/
	private static StringFields stringFields = new StringFields();
	
	/**
	 * 返回异常文本。
	 * @param key 异常文本的主键。
	 * @return 主键对应的文本。
	 */
	public static String getException(ExceptionStringFieldKey key){
		return stringFields.getException(key);
	}
	
	/**
	 * 返回标签文本。
	 * @param key 标签文本的主键。
	 * @return  主键对应的文本。
	 */
	public static String getLabel(LabelStringFieldKey key){
		return stringFields.getLabel(key);
	}
	
	/**
	 * 返回信息文本。
	 * @param key 信息文本的主键。
	 * @return 主键对应的文本。
	 */
	public static String getMessage(MessageStringFieldKey key){
		return stringFields.getMessage(key);
	}
	
	
	
	private SModuleMgr module;
	private SViewMgr view;
	private SControlMgr control;
	
	private Scheduler(SModuleMgr module, SViewMgr view, SControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
	}
	
	
}
