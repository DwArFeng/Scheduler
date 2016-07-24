package com.dwarfeng.scheduler.core.module;

import com.dwarfeng.dwarffunction.program.mvc.ModuleManager;
import com.dwarfeng.scheduler.core.SProgramConstField;

public final class SDefModuleManager implements ModuleManager<SModuleControlPort, SProgramConstField> {

	private final SModuleControlPort moduleControlPort = new SModuleControlPort() {
		
		
	};
	
	private SProgramConstField programConstField;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ModuleManager#getModuleControlPort()
	 */
	@Override
	public SModuleControlPort getModuleControlPort() {
		return this.moduleControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ModuleManager#getProgramConstField()
	 */
	@Override
	public SProgramConstField getProgramConstField() {
		return this.programConstField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ModuleManager#setProgramConstField(com.dwarfeng.dwarffunction.program.mvc.ProgramConstField)
	 */
	@Override
	public void setProgramConstField(SProgramConstField programConstField) {
		this.programConstField = programConstField;
	}

}
