package com.dwarfeng.scheduler.typedef.abstruct;


/**
 * ����Ĳ��ڹ������ڵĹ��̶���
 * <p> �������̶ȵ�ʵ��{@linkplain ObjectOutProjectTree} �ӿڡ�
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
