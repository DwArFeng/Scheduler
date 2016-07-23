package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ControlMgr {

	/**
	 * 获取控制器的视图控制站。
	 * @return 控制器的视图控制站。
	 */
	public ViewControlPort getViewControlPort();
	
	/**
	 * 设置控制器的视图控制站。
	 * @param viewControlPort 指定的视图控制站。
	 */
	public void setViewControlPort(ViewControlPort viewControlPort);
	
	/**
	 * 获取程序的控制站点。 
	 * @return 程序的控制站点。
	 */
	public SchedulerControlPort getSchedulerControlPort();
	
	/**
	 * 设置程序控制站点。
	 * @param schedulerControlPort 程序控制站点。
	 */
	public void setSchedulerControlPort(SchedulerControlPort schedulerControlPort);
	
	/**
	 * 获取控制管理器的控制站点。
	 * @return 控制站点。
	 */
	public ControlPort getControlPort();
	
}
