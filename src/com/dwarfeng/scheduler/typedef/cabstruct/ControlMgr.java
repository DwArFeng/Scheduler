package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ControlMgr {

	/**
	 * ��ȡ����������ͼ����վ��
	 * @return ����������ͼ����վ��
	 */
	public ViewControlPort getViewControlPort();
	
	/**
	 * ���ÿ���������ͼ����վ��
	 * @param viewControlPort ָ������ͼ����վ��
	 */
	public void setViewControlPort(ViewControlPort viewControlPort);
	
	/**
	 * ��ȡ����Ŀ���վ�㡣 
	 * @return ����Ŀ���վ�㡣
	 */
	public SchedulerControlPort getSchedulerControlPort();
	
	/**
	 * ���ó������վ�㡣
	 * @param schedulerControlPort �������վ�㡣
	 */
	public void setSchedulerControlPort(SchedulerControlPort schedulerControlPort);
	
	/**
	 * ��ȡ���ƹ������Ŀ���վ�㡣
	 * @return ����վ�㡣
	 */
	public ControlPort getControlPort();
	
}
