package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.project.Project;

/**
 * �����쳣��
 * <p> ���й���Դָʾ���쳣������쳣ͬ�������׳����幤�̳��ֵ��쳣������ĳ�����̱������ʧ�ܣ����
 * �׳�����쳣��
 * <br>ͨ����˵���׳����쳣ʱ���������࣬��Ϊ���Ǹ��Ӿ��塣
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectException extends Exception {

	private static final long serialVersionUID = -1012496247156385961L;
	
	/**�����쳣�Ĺ���Դ*/
	protected final Project project;
	
	/**
	 * ����һ��Ĭ�ϵĹ����쳣��
	 */
	public ProjectException() {
		this(null,null);
	}
	
	/**
	 * ����һ��ָ���쳣Դ�Ĺ����쳣��
	 * @param project ָ�����쳣Դ��
	 */
	public ProjectException(Project project) {
		this(project,null);
	}

	/**
	 * ����һ������ָ�����쳣Դ��ָ������Ϣ�Ĺ����쳣��
	 * @param project ָ�����쳣Դ�� 
	 * @param message ָ������Ϣ��
	 */
	public ProjectException(Project project,String message) {
		super(message);
		this.project = project;
	}
	
	/**
	 * �����쳣Դ��
	 * @return �쳣Դ���̡�
	 */
	public Project getProject(){
		return this.project;
	}

}
