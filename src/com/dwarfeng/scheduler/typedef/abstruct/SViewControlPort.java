package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.dwarffunction.program.mvc.ViewControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public interface SViewControlPort extends ViewControlPort {

	/**
	 * ����ͼ����ʾ��Ӧ�������Ϣ����
	 * @param pack �����Ϣ����
	 */
	public void showMessage(OutputMessagePack pack);
	
}
