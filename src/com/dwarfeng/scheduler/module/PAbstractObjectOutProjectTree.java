package com.dwarfeng.scheduler.module;



/**
 * 抽象的不在工程树内的工程对象。
 * <p> 该类最大程度的实现{@linkplain PObjectOutProjectTree} 接口。
 * @author DwArFeng
 * @since 1.8
 */
abstract class PAbstractObjectOutProjectTree implements PObjectOutProjectTree{

	protected PObjectInProject context;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree#getContext()
	 */
	@Override
	public PObjectInProject getContext() {
		return this.context;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree#setContext(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject)
	 */
	@Override
	public void setContext(PObjectInProject context) {
		this.context = context;
	}

}
