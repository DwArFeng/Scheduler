package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ViewMgr {
	
	/**
	 * ������ͼ���������еĿ��ƹ����������á�
	 * @return ���ƹ�������
	 */
	public ControlMgr getControlMgr();
	
	/**
	 * ������ͼ���������еĿ��ƹ����������á�
	 * @param controlMgr ���ƹ�������
	 */
	public void setControlMgr(ControlMgr controlMgr);
	
	/**
	 * ��ȡ����Ŀ���վ��
	 * @return ����Ŀ���վ��
	 */
	public ViewControlPort getViewControlPort();
	
	/**
	 * ��ȡ����ѯ��վ��
	 * @return �����ѯ��վ��
	 */
	public ViewAskPort getViewAskPort();

}
