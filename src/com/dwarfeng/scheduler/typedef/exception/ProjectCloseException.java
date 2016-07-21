package com.dwarfeng.scheduler.typedef.exception;

import java.io.File;

import com.dwarfeng.scheduler.project.Project;

/**
 * �ļ����������ر�ʱ�׳����쳣��
 * @author DwArFeng
 * @since 1.8
 */
public final class ProjectCloseException extends ProjectException {

	private static final long serialVersionUID = 7489611659269338555L;
	
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
		super(project,message);
		this.workspace = workspace;
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
