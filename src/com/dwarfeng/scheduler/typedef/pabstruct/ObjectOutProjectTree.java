package com.dwarfeng.scheduler.typedef.pabstruct;

import com.dwarfeng.scheduler.project.Project;

/**
 * 在工程树之外的以工程对象为下文的类。
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectOutProjectTree extends ObjectInProject {

	/**
	 * 获取该类的工程对象上文。
	 * @return 工程对象上文。
	 */
	public ObjectInProject getContext();
	
	/**
	 * 设置该类工程对上文。
	 * @param context 指定的工程对象上文。
	 */
	public void setContext(ObjectInProject context);
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject#getRootProject()
	 */
	@Override
	public default Project getRootProject(){
		if(getContext() == null) return null;
		return getContext().getRootProject();
	}
}
