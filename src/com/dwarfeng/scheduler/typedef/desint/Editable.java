package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JDesktopPane;

import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;

/**
 * �ɱ༭�ӿڡ�
 * <p>ֻҪ���ܱ༭�Ķ��󣬶�Ӧ��ʵ������ӿڡ�
 * <br>��ν�ı༭����ָ�ڳ���������({@linkplain JDesktopPane})������һ����Ӧ�ı༭���ڣ�
 * �������������������ݣ��������ԣ��ĸ��ģ���Ϊ�������總���ı������ڿɱ༭�Ķ���
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface Editable extends ObjectInProject{
	
	/**
	 * ���ر༭�����������Ĵ��ڵı��⡣
	 * @return �����ı���
	 */
	public String getEditorTitle();
	/**
	 * ���������ڸÿɱ༭����ı༭���档
	 * @return �༭���档
	 * @throws Exception ��ñ༭��ʱ�����쳣��
	 */
	public AbstractEditor getEditor() throws Exception ;
	
	/**
	 * ��ָ�༭���ر�ʱ���ɱ༭��֪ͨ�ɱ༭����ķ�����
	 */
	public void firedEditorClose();
	
	/**
	 * ������Ҫ�༭�Ķ���
	 * @throws Exception �����쳣��
	 */
	public void load() throws Exception;
	
	/**
	 * ������Ҫ�༭�Ķ���
	 * @throws Exception �����쳣��
	 */
	public void save() throws Exception;
	
	/**
	 * �ͷ��ڴ档
	 */
	public void release();
	
//	/**
//	 * ��������༭����һ�����Է��ض��ٸ��ṩ�༭�Ķ���
//	 * @return �ɹ��༭�Ķ����������
//	 */
//	public int getTotleTarget();
//	
//	/**
//	 * ������ָ��λ�õĿɱ༭�Ķ���
//	 * @param index ָ����λ�á�
//	 * @return �ɹ��༭�Ķ���
//	 * @throws IndexOutOfBoundsException λ�ó���ʱ�׳����쳣��
//	 */
//	public Object getTarget(int index) throws IndexOutOfBoundsException;
	
}
