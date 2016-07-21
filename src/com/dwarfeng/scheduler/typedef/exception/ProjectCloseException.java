package com.dwarfeng.scheduler.typedef.exception;

import java.io.File;

import com.dwarfeng.scheduler.project.Project;

/**
 * 文件不能正常关闭时抛出的异常。
 * @author DwArFeng
 * @since 1.8
 */
public final class ProjectCloseException extends ProjectException {

	private static final long serialVersionUID = 7489611659269338555L;
	
	private final File workspace;
	
	/**
	 * 生成一个工程关闭异常。
	 * @param project 指定的工程。 
	 * @param workspace 指定的工作路径。
	 */
	public ProjectCloseException(Project project,File workspace) {
		this(project,workspace,null);
	}
	
	/**
	 * 生成一个工程关闭异常。
	 * @param project 指定的工程。 
	 * @param workspace 指定的工作路径。
	 * @param message 异常的描述。
	 */
	public ProjectCloseException(Project project,File workspace,String message){
		super(project,message);
		this.workspace = workspace;
	}

	/**
	 * 返回异常工程对应的工程文件。
	 * @return 工程文件。
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
