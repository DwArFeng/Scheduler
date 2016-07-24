package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * �ɱ༭�����쳣��
 * <p> ���쳣ͨ�������ڶԿɱ༭������б༭ʱ���翪ʼ�༭ʱ�ļ���ʧ�ܣ��༭ʱ���б���ı���ʧ�ܵȡ�
 * @author DwArFeng
 * @since 1.8
 */
public class EditableException extends Exception {
	
	private static final long serialVersionUID = 4558641177821370674L;
	
	/**�쳣�Ŀɱ༭����Դ*/
	private final Editable editable;

	/**
	 * ����Ĭ�ϵĿɱ༭�����쳣��
	 * @param editable ָ�����쳣Դ��
	 */
	public EditableException(Editable editable) {
		this(editable,null);
	}

	/**
	 * ���ɾ���ָ����Ϣ�Ŀɱ༭�����쳣��
	 * @param editable ָ�����쳣Դ��
	 * @param message ָ������Ϣ��
	 */
	public EditableException(Editable editable,String message) {
		super(message);
		this.editable = editable;
	}

	/**
	 * ���ظ��쳣���쳣Դ��
	 * @return ���쳣���쳣Դ��
	 */
	public Editable getEditable() {
		return editable;
	}

}
