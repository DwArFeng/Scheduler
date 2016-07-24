package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.project.Project;

/**
 * 在工程文件接口。
 * <p> 该接口指示了一个类是属于工程的一个子类，实现该接口即意味着该对象能够被直接添加进Project中。
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectInProject{
	
	/**
	 * 返回该对象的Project根。
	 * @return 对象隶属的Project。
	 */
	public Project getRootProject();
	
}
