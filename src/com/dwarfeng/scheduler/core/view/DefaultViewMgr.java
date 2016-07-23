package com.dwarfeng.scheduler.core.view;

import javax.swing.JFrame;

import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr;

public final class DefaultViewMgr implements ViewMgr {

	/**持有的控制器引用*/
	private ControlPort controlPort = null;
	
	private MainFrame mainFrame = null;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr#getViewControlPort()
	 */
	@Override
	public ViewControlPort getViewControlPort() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr#getControlMgr()
	 */
	@Override
	public ControlPort getControlMgr() {
		return this.controlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr#setControlPort(com.dwarfeng.scheduler.typedef.cabstruct.ControlPort)
	 */
	@Override
	public void setControlPort(ControlPort controlPort) {
		this.controlPort = controlPort;
	}
	
	/**
	 * 整个程序的主界面。
	 * @author DwArFeng
	 * @since 1.8
	 */
	private class MainFrame extends JFrame{
		
	}

}
