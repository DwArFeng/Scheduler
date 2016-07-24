package com.dwarfeng.scheduler.module;

import com.dwarfeng.dwarffunction.program.mvc.ModuleManager;
import com.dwarfeng.scheduler.core.SProgramConstField;

public final class SModuleManager implements ModuleManager<SModuleControlPort, SProgramConstField> {

	private final SModuleControlPort moduleControlPort = new SModuleControlPort() {
		
	};
	
	/**���õĳ��򼶳���*/
	private SProgramConstField programConstField;
	/**���������ʱ����*/
	private SRuntimeVar runtimeVar;
	
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
