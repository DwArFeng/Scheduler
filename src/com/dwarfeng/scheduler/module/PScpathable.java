package com.dwarfeng.scheduler.module;

import com.dwarfeng.scheduler.module.project.abstruct.ObjectOutProjectTree;


/**
 * ·���ࡣ
 * <p> ʵ�ָ���Ķ�����ζ�����ڹ����д������빤��·����ĳһ���ļ���ӳ�䡣
 * @author DwArFeng
 * @since 1.8
 */
public interface PScpathable extends ObjectOutProjectTree{
	
	/**
	 * ��ȡ������·����
	 * @return ������·����
	 */
	public Scpath getScpath();
}
