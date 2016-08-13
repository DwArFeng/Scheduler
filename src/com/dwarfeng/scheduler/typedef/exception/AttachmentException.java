package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.io.Scpath;

/**
 * �����쳣��
 * <p> ���쳣�ڸ�������ȡ�򱣴浽��ʱ������
 * @author DwArFeng
 * @since 1.8
 */
public final class AttachmentException extends Exception {

	private static final long serialVersionUID = 961446227167629923L;
	
	/**����ָʾ�Ķ���*/
	private final Object att;
	/**������·��*/
	private final Scpath scpath;
	
	/**
	 * ����һ������ָ���ĸ�����ָ��·���ĸ����쳣��
	 * @param att ָ���ĸ�����
	 * @param scpath ָ����·����
	 */
	public AttachmentException(Object att,Scpath scpath) {
		this(att,scpath,null,null);
	}

	/**
	 * ����һ������ָ���ĸ�����ָ����·����ָ�����쳣��Ϣ�ĸ����쳣��
	 * @param att ָ���ĸ�����
	 * @param scpath ָ����·����
	 * @param message ָ�����쳣��Ϣ��
	 */
	public AttachmentException(Object att,Scpath scpath,String message) {
		this(att,scpath,message,null);
	}

	/**
	 * ����һ������ָ��������ָ����·����ָ�����쳣ԭ��ĸ����쳣��
	 * @param att ָ���ĸ�����
	 * @param scpath ָ����·����
	 * @param cause ָ�����쳣ԭ��
	 */
	public AttachmentException(Object att,Scpath scpath,Throwable cause) {
		this(att,scpath,null,cause);
	}

	/**
	 * ����һ������ָ���ĸ�����ָ����·����ָ�����쳣��Ϣ��ָ�����쳣ԭ��ĸ����쳣��
	 * @param att ָ���ĸ�����
	 * @param scpath ָ����·����
	 * @param message ָ�����쳣��Ϣ��
	 * @param cause ָ�����쳣ԭ��
	 */
	public AttachmentException(Object att,Scpath scpath,String message, Throwable cause) {
		super(message, cause);
		this.att = att;
		this.scpath = scpath;
	}
	
	/**
	 * �����쳣�еĸ�����
	 * @return ָ���ĸ�����
	 */
	public Object getAttObject(){
		return this.att;
	}
	
	/**
	 * �����쳣�е�·����
	 * @return �쳣�е�·����
	 */
	public Scpath getScpath(){
		return scpath;
	}


}
