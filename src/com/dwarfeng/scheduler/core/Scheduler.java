package com.dwarfeng.scheduler.core;

import com.dwarfeng.scheduler.typedef.cabstruct.SControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SViewMgr;

/**
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p> 该类中的所有方法均不是线程安全的，如需要多线程调用，请使用外部同步。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler {
	
	
	private SModuleMgr module;
	private SViewMgr view;
	private SControlMgr control;
	
	private Scheduler(SModuleMgr module, SViewMgr view, SControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
	}
	
	
}
