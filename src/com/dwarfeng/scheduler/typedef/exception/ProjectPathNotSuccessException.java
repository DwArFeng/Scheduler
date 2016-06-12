package com.dwarfeng.scheduler.typedef.exception;

import java.util.ArrayList;
import java.util.List;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.project.Project;

/**
 * 工程路径不成功异常。
 * <p> 该异常指示着工程路径不能完整的被读取或者被存储。
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectPathNotSuccessException extends Exception{
	
	/**
	 * 指示着工程在进行何种调度时发生了不成功的现象。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**在读取的时候出现失败的标记*/
		LOAD,
		/**在存储的时候出现失败的标记*/
		SAVE;
	}
	
	private final FailedType failedType;
	private final Project project;
	private final List<Scpath> successList;
	private final List<Scpath> failedList;

	/**
	 * 生成一个工程路径不成功异常。
	 * @param project 失败的工程。
	 * @param successList 工程中的成功路径列表。
	 * @param failedList 工程中失败的路径列表。
	 */
	public ProjectPathNotSuccessException(Project project,List<Scpath> successList,List<Scpath> failedList,FailedType failedType) {
		this.project = project;
		this.successList = successList;
		this.failedList = failedList;
		this.failedType = failedType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		String str = super.getMessage();
		if(str == null || str.equals("")){
			str = "Some path " + (failedType == FailedType.LOAD ? "loaded" : "saved") + " unsuccessfully"
					+ " : " + failedList.size() + "fails";
		}
		return str;
	}

	/**
	 * 返回失败的工程。
	 * @return 失败的工程。
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * 返回成功的读取列表。
	 * @return 成功的读取列表。
	 */
	public List<Scpath> getSuccessList() {
		return new ArrayList<Scpath>(successList);
	}

	/**
	 * 返回失败列表。
	 * @return 失败列表。
	 */
	public List<Scpath> getFailedList() {
		return new ArrayList<Scpath>(failedList);
	}

	/**
	 * 返回失败类型。
	 * @return 失败类型。
	 */
	public FailedType getFailedType() {
		return failedType;
	}
}
