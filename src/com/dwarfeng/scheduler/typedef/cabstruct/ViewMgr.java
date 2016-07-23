package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ViewMgr {
	
	/**
	 * 返回视图管理器持有的控制管理器的引用。
	 * @return 控制管理器。
	 */
	public ControlPort getControlMgr();
	
	/**
	 * 设置视图管理器持有的控制管理器的引用。
	 * @param controlMgr 控制管理器。
	 */
	public void setControlPort(ControlPort controlPort);
	
	/**
	 * 获取自身的控制站。
	 * @return 自身的控制站。
	 */
	public ViewControlPort getViewControlPort();

}
