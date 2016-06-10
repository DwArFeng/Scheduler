package com.dwarfeng.scheduler.typedef.desint;


/**
 * �༭�ӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Editor {
	
	/**
	 * ��ȡ���еĿɱ༭����
	 * @return �ɱ༭����
	 */
	public Editable getEditable();
	
	/**
	 * �����еĿɱ༭������ж�ȡ������
	 * <p> ע�⣬�༭���������༭���̵���㣬
	 * ��ˣ������ڶ�ȡ�ɱ������ʱ�����׳����κ��쳣��Ҫ���г�ֵĴ���
	 */
	public void loadEditable();
	
	/**
	 * �����еĿɱ༭������д洢������
	 * <p> ע�⣬�༭���������༭���̵���㣬
	 * ��ˣ������ڶ��洢�������ʱ�����׳����κ��쳣��Ҫ���г�ֵĴ���
	 */
	public void saveEditable();
}
