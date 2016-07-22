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
	 * 获取控制器的视图询问站。
	 * @return 控制器的视图询问站。
	 */
	public ViewAskPort getViewAskPort();
	
	/**
	 * 设置控制器的视图询问站。
	 * @param viewAskPort 控制器的视图询问站。
	 */
	public void setViewAskPort(ViewAskPort viewAskPort);
	
	/**
	 * 使程序显示指定的信息包中对应的输出信息。
	 * @param pack 指定的信息包。
	 */
	public default void showMessage(MessagePack pack){
		if(getViewControlPort() != null){
			getViewControlPort().showMessage(pack);
		}else{
			return;
		}
	}
	
}
