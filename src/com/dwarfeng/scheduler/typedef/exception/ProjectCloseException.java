package com.dwarfeng.scheduler.typedef.exception;

import java.io.File;

import com.dwarfeng.scheduler.project.Project;

/**
 * �ļ����������ر�ʱ�׳����쳣��
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectCloseException extends Exception {

	private final Project project;
	private final File workspace;
	
	/**
	 * ����һ�����̹ر��쳣��
	 * @param project ָ���Ĺ��̡� 
	 * @param workspace ָ���Ĺ���·����
	 */
	public ProjectCloseException(Project project,File workspace) {
		this(project,workspace,null);
	}
	
	/**
	 * ����һ�����̹ر��쳣��
	 * @param project ָ���Ĺ��̡� 
	 * @param workspace ָ���Ĺ���·����
	 * @param message �쳣��������
	 */
	public ProjectCloseException(Project project,File workspace,String message){
		super(message);
		this.project = project;
		this.workspace = workspace;
	}

	/**
	 * �����쳣�Ĺ��̡�
	 * @return �쳣�Ĺ��̡�
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * �����쳣���̶�Ӧ�Ĺ����ļ���
	 * @return �����ļ���
	 */
	public File getWorkspace() {
		return workspace;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		String str = super.getMessage();
		return str == null || str.equals("") ? "Project can't close": str;
	}
	
}
