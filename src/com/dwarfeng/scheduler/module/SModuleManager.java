package com.dwarfeng.scheduler.module;

import com.dwarfeng.dwarffunction.program.mvc.ModuleManager;
import com.dwarfeng.scheduler.core.SchedulerAttrSet;

public final class SModuleManager implements ModuleManager<SModuleControlPort, SchedulerAttrSet> {

	private final SModuleControlPort moduleControlPort = new SModuleControlPort() {
		
	};
	
	/**���õĳ��򼶳���*/
	private SchedulerAttrSet programConstField;
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
	public SchedulerAttrSet getProgramConstField() {
		return this.programConstField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ModuleManager#setProgramConstField(com.dwarfeng.dwarffunction.program.mvc.ProgramConstField)
	 */
	@Override
	public void setProgramConstField(SchedulerAttrSet programConstField) {
		this.programConstField = programConstField;
	}

	@Override
	public SchedulerAttrSet getProgramAttrSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramAttrSet(SchedulerAttrSet programAttrSet) {
		// TODO Auto-generated method stub
		
	}

}
