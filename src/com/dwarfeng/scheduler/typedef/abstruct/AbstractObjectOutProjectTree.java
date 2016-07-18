package com.dwarfeng.scheduler.typedef.abstruct;


/**
 * 抽象的不在工程树内的工程对象。
 * <p> 该类最大程度的实现{@linkplain ObjectOutProjectTree} 接口。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractObjectOutProjectTree implements ObjectOutProjectTree{

	protected ObjectInProject context;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree#getContext()
	 */
	@Override
	public ObjectInProject getContext() {
		return this.context;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree#setContext(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject)
	 */
	@Override
	public void setContext(ObjectInProject context) {
		this.context = context;
	}

}
