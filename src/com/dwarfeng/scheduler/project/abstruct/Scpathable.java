package com.dwarfeng.scheduler.project.abstruct;

import com.dwarfeng.scheduler.module.PObjectOutProjectTree;
import com.dwarfeng.scheduler.module.Scpath;

/**
 * ·���ࡣ
 * <p> ʵ�ָ���Ķ�����ζ�����ڹ����д������빤��·����ĳһ���ļ���ӳ�䡣
 * @author DwArFeng
 * @since 1.8
 */
public interface Scpathable extends PObjectOutProjectTree{
	
	/**
	 * ��ȡ������·����
	 * @return ������·����
	 */
	public Scpath getScpath();
}
