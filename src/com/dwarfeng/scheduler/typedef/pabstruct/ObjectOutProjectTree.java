package com.dwarfeng.scheduler.typedef.pabstruct;

import com.dwarfeng.scheduler.project.Project;

/**
 * �ڹ�����֮����Թ��̶���Ϊ���ĵ��ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectOutProjectTree extends ObjectInProject {

	/**
	 * ��ȡ����Ĺ��̶������ġ�
	 * @return ���̶������ġ�
	 */
	public ObjectInProject getContext();
	
	/**
	 * ���ø��๤�̶����ġ�
	 * @param context ָ���Ĺ��̶������ġ�
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
