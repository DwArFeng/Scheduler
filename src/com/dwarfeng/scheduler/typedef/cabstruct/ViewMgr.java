package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ViewMgr {
	
	/**
	 * ������ͼ���������еĿ��ƹ����������á�
	 * @return ���ƹ�������
	 */
	public ControlPort getControlMgr();
	
	/**
	 * ������ͼ���������еĿ��ƹ����������á�
	 * @param controlMgr ���ƹ�������
	 */
	public void setControlPort(ControlPort controlPort);
	
	/**
	 * ��ȡ����Ŀ���վ��
	 * @return ����Ŀ���վ��
	 */
	public ViewControlPort getViewControlPort();

}
