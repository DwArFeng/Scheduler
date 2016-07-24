package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.dwarffunction.program.mvc.ControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public interface SControlPort extends ControlPort{
	
	/**
	 * 使视图输出信息包中的信息。
	 * @param pack 输出信息包。
	 */
	public void showMessage(OutputMessagePack pack);

}
