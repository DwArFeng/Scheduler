package com.dwarfeng.scheduler.typedef.cabstruct;

/**
 * ��ͼ����վ��
 * <p> �ÿ���վ�㱻{@linkplain ControlMgr}���ã���Ҫʱֱ�ӵ������еķ����������Խ���Ŀ��ơ�
 * @author DwArFeng
 * @since 1.8
 */
public interface ViewControlPort {

	/**
	 * �ڽ�������ʾָ����Ϣ���е�����ı���
	 * @param pack ָ������Ϣ����
	 */
	public void showMessage(MessagePack pack);
	
}
