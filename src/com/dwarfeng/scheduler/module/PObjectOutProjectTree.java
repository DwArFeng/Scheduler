package com.dwarfeng.scheduler.module;


/**
 * �ڹ�����֮����Թ��̶���Ϊ���ĵ��ࡣ
 * @author DwArFeng
 * @since 1.8
 */
interface PObjectOutProjectTree extends PObjectInProject {

	/**
	 * ��ȡ����Ĺ��̶������ġ�
	 * @return ���̶������ġ�
	 */
	public PObjectInProject getContext();
	
	/**
	 * ���ø��๤�̶����ġ�
	 * @param context ָ���Ĺ��̶������ġ�
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
