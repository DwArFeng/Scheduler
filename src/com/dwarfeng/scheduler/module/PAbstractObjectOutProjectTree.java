package com.dwarfeng.scheduler.module;



/**
 * ����Ĳ��ڹ������ڵĹ��̶���
 * <p> �������̶ȵ�ʵ��{@linkplain PObjectOutProjectTree} �ӿڡ�
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
