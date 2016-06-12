package com.dwarfeng.scheduler.typedef.abstruct;

/**
 * �����ӿڡ�ʵ�ָýӿڵĶ����ǹ����ļ���һ���֣�������ռ��
 * ���̽ṹ��ͬʱ�����빤���е�ĳ���ļ������ӡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Attachment extends ObjectInProject,Scpathable{
	
	/**
	 * ��ȡ����ָ����ĵ���
	 */
	public void load() throws Exception;
	
	/**
	 * ���渽��ָ����ĵ���
	 * @throws Exception �����쳣��
	 */
	public void save() throws Exception;
	
	/**
	 * �ͷŸ����ĵ���
	 */
	public void release();
	
	/**
	 * ���Ŀ�����
	 * <p> �ö������븽���ļ�ֱ�ӹ����Ķ����뱣֤�÷�����Զ�����ؿ�ֵ��
	 * <p> ���Ŀ�����Ϊ�գ��򷵻�{@linkplain Attachment#createDefaultObject()}�еĶ���
	 * @return Ŀ�����
	 */
	public Object getAttachObject();
	
	/**
	 * ����Ŀ�����Ϊָ���Ķ���
	 * @param target ָ���Ķ���
	 */
	public void setAttachObject(Object obj);
	/**
	 * ����Ĭ�ϵĸ�������
	 * @return Ĭ�ϵĸ�������
	 */
	public Object createDefaultObject();
}
