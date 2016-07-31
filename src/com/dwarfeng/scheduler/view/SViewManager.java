package com.dwarfeng.scheduler.view;

import com.dwarfeng.dwarffunction.program.mvc.ViewManager;
import com.dwarfeng.scheduler.core.SProgramConstField;
import com.dwarfeng.scheduler.project.abstruct.SControlPort;
import com.dwarfeng.scheduler.project.abstruct.SViewControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public final class SViewManager implements ViewManager<SViewControlPort, SControlPort, SProgramConstField> {

	private final SViewControlPort ViewControlPort = new SViewControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.view.SViewControlPort#showMessage(com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack)
		 */
		@Override
		public void showMessage(OutputMessagePack pack) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private SControlPort controlPort;
	private SProgramConstField programConstField;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ViewManager#getViewControlPort()
	 */
	@Override
	public SViewControlPort getViewControlPort() {
		return this.ViewControlPort;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ViewManager#getControlPort()
	 */
	@Override
	public SControlPort getControlPort() {
		return this.controlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ViewManager#getProgramConstField()
	 */
	@Override
	public SProgramConstField getProgramConstField() {
		return this.programConstField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ViewManager#setProgramConstField(com.dwarfeng.dwarffunction.program.mvc.ProgramConstField)
	 */
	@Override
	public void setProgramConstField(SProgramConstField programConstField) {
		this.programConstField = programConstField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ViewManager#setControlPort(com.dwarfeng.dwarffunction.program.mvc.ControlPort)
	 */
	@Override
	public void setControlPort(SControlPort controlPort) {
		this.controlPort = controlPort;
	}

	

}
