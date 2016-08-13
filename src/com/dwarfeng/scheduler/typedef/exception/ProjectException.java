package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.project.Project;

/**
 * 工程异常。
 * <p> 带有工程源指示的异常。这个异常同样用来抛出具体工程出现的异常，比如某个工程保存后发生失败，则会
 * 抛出这个异常。
 * <br>通常来说，抛出的异常时这个类的子类，因为它们更加具体。
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectException extends Exception {

	private static final long serialVersionUID = -1012496247156385961L;
	
	/**发生异常的工程源*/
	protected final Project project;
	
	/**
	 * 生成一个默认的工程异常。
	 */
	public ProjectException() {
		this(null,null);
	}
	
	/**
	 * 生成一个指定异常源的工程异常。
	 * @param project 指定的异常源。
	 */
	public ProjectException(Project project) {
		this(project,null);
	}

	/**
	 * 生成一个具有指定的异常源，指定的信息的工程异常。
	 * @param project 指定的异常源。 
	 * @param message 指定的信息。
	 */
	public ProjectException(Project project,String message) {
		super(message);
		this.project = project;
	}
	
	/**
	 * 返回异常源。
	 * @return 异常源工程。
	 */
	public Project getProject(){
		return this.project;
	}

}
