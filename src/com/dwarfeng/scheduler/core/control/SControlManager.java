package com.dwarfeng.scheduler.core.control;

import com.dwarfeng.dwarffunction.program.mvc.ControlManager;
import com.dwarfeng.scheduler.core.module.SModuleControlPort;
import com.dwarfeng.scheduler.core.view.SViewControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.MessagePack;
import com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewControlPort;

public final class SControlManager implements ControlManager<SProgramControlPort, SModuleControlPort, SViewControlPort, SControlPort>{
	
	private final SControlPort controlPort = new SControlPort() {
		
		@Override
		public void showMessage(MessagePack pack) {
			if(viewControlPort != null){
				viewControlPort.showMessage(pack);
			}
		}
	};
	
	private ViewControlPort viewControlPort;
	private SchedulerControlPort schedulerControlPort;

}
