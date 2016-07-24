package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.dwarffunction.program.mvc.ViewControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public interface SViewControlPort extends ViewControlPort {

	/**
	 * 在视图上显示对应的输出信息包。
	 * @param pack 输出信息包。
	 */
	public void showMessage(OutputMessagePack pack);
	
}
