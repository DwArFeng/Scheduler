package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.io.Scpath;

/**
 * ·���ࡣ
 * <p> ʵ�ָ���Ķ�����ζ�����ڹ����д������빤��·����ĳһ���ļ���ӳ�䡣
 * @author DwArFeng
 * @since 1.8
 */
public interface Scpathable extends ObjectInProject{
	
	/**
	 * ��ȡ������·����
	 * @return ������·����
	 */
	public Scpath getScpath();
}
