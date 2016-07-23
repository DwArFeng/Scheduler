package com.dwarfeng.scheduler.core.control;

import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.MessagePack;
import com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewControlPort;

public final class DefaultControlMgr implements ControlMgr {
	
	private final ControlPort controlPort = new ControlPort() {
		
		@Override
		public void showMessage(MessagePack pack) {
			if(viewControlPort != null){
				viewControlPort.showMessage(pack);
			}
		}
	};
	
	private ViewControlPort viewControlPort;
	private SchedulerControlPort schedulerControlPort;

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr#getViewControlPort()
	 */
	@Override
	public ViewControlPort getViewControlPort() {
		return this.viewControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr#setViewControlPort(com.dwarfeng.scheduler.typedef.cabstruct.ViewControlPort)
	 */
	@Override
	public void setViewControlPort(ViewControlPort viewControlPort) {
		this.viewControlPort = viewControlPort;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr#getSchedulerControlPort()
	 */
	@Override
	public SchedulerControlPort getSchedulerControlPort() {
		return this.schedulerControlPort;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr#setSchedulerControlPort(com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort)
	 */
	@Override
	public void setSchedulerControlPort(SchedulerControlPort schedulerControlPort) {
		this.schedulerControlPort = schedulerControlPort;
	}

	@Override
	public ControlPort getControlPort() {
		return controlPort;
	}

}
