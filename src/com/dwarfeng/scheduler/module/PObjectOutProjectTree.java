package com.dwarfeng.scheduler.module;


/**
 * 在工程树之外的以工程对象为下文的类。
 * @author DwArFeng
 * @since 1.8
 */
interface PObjectOutProjectTree extends PObjectInProject {

	/**
	 * 获取该类的工程对象上文。
	 * @return 工程对象上文。
	 */
	public PObjectInProject getContext();
	
	/**
	 * 设置该类工程对上文。
	 * @param context 指定的工程对象上文。
	 */
	public void setContext(PObjectInProject context);
	
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
