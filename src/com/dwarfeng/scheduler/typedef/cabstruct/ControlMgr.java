package com.dwarfeng.scheduler.typedef.cabstruct;

public interface ControlMgr {

	/**
	 * ��ȡ����������ͼ����վ��
	 * @return ����������ͼ����վ��
	 */
	public ViewControlPort getViewControlPort();
	
	/**
	 * ���ÿ���������ͼ����վ��
	 * @param viewControlPort ָ������ͼ����վ��
	 */
	public void setViewControlPort(ViewControlPort viewControlPort);
	
	/**
	 * ��ȡ����������ͼѯ��վ��
	 * @return ����������ͼѯ��վ��
	 */
	public ViewAskPort getViewAskPort();
	
	/**
	 * ���ÿ���������ͼѯ��վ��
	 * @param viewAskPort ����������ͼѯ��վ��
	 */
	public void setViewAskPort(ViewAskPort viewAskPort);
	
	/**
	 * ʹ������ʾָ������Ϣ���ж�Ӧ�������Ϣ��
	 * @param pack ָ������Ϣ����
	 */
	public default void showMessage(MessagePack pack){
		if(getViewControlPort() != null){
			getViewControlPort().showMessage(pack);
		}else{
			return;
		}
	}
	
}
