package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.core.module.Scpath;

/**
 * 路径类。
 * <p> 实现该类的对象意味着其在工程中存在着与工作路径的某一个文件的映射。
 * @author DwArFeng
 * @since 1.8
 */
public interface Scpathable extends ObjectOutProjectTree{
	
	/**
	 * 获取附件的路径。
	 * @return 附件的路径。
	 */
	public Scpath getScpath();
}
