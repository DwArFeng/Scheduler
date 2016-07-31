package com.dwarfeng.scheduler.core;

import com.dwarfeng.dwarffunction.program.mvc.ControlManager;
import com.dwarfeng.scheduler.module.SModuleControlPort;
import com.dwarfeng.scheduler.project.abstruct.SControlPort;
import com.dwarfeng.scheduler.project.abstruct.SViewControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public final class SControlManager implements ControlManager<SProgramControlPort, SModuleControlPort, SViewControlPort, SControlPort>{
	
	private final SControlPort controlPort = new SControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.control.SControlPort#showMessage(com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack)
		 */
		@Override
		public void showMessage(OutputMessagePack pack) {
			if(viewControlPort != null){
				viewControlPort.showMessage(pack);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dwarffunction.program.mvc.ControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			// TODO Auto-generated method stub
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dwarffunction.program.mvc.ControlPort#endProgram()
		 */
		@Override
		public void endProgram() {
			// TODO Auto-generated method stub
			
		}
	};

	private SProgramControlPort programControlPort;
	private SModuleControlPort moduleControlPort;
	private SViewControlPort viewControlPort;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#getControlPort()
	 */
	@Override
	public SControlPort getControlPort() {
		return controlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#getModuleControlPort()
	 */
	@Override
	public SModuleControlPort getModuleControlPort() {
		return moduleControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#getProgramControlPort()
	 */
	@Override
	public SProgramControlPort getProgramControlPort() {
		return programControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#getViewControlPort()
	 */
	@Override
	public SViewControlPort getViewControlPort() {
		return viewControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#setModuleControlPort(com.dwarfeng.dwarffunction.program.mvc.ModuleControlPort)
	 */
	@Override
	public void setModuleControlPort(SModuleControlPort moduleControlPort) {
		this.moduleControlPort = moduleControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#setViewControlPort(com.dwarfeng.dwarffunction.program.mvc.ViewControlPort)
	 */
	@Override
	public void setViewControlPort(SViewControlPort viewControlPort) {
		this.viewControlPort = viewControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ControlManager#setProgramControlPort(com.dwarfeng.dwarffunction.program.mvc.ProgramControlPort)
	 */
	@Override
	public void setProgramControlPort(SProgramControlPort programControlPort) {
		this.programControlPort = programControlPort;
	}

	
	

}
