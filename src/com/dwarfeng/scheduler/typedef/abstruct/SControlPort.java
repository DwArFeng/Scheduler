package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.dwarffunction.program.mvc.ControlPort;
import com.dwarfeng.scheduler.typedef.conmod.OutputMessagePack;

public interface SControlPort extends ControlPort{
	
	/**
	 * ʹ��ͼ�����Ϣ���е���Ϣ��
	 * @param pack �����Ϣ����
	 */
	public void showMessage(OutputMessagePack pack);

}
