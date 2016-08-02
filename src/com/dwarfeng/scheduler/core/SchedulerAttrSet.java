package com.dwarfeng.scheduler.core;

import java.awt.Image;

import com.dwarfeng.dwarffunction.program.Version;
import com.dwarfeng.dwarffunction.program.mvc.ProgramAttrSet;
import com.dwarfeng.dwarffunction.program.mvc.ProgramConstField;
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;

/**
 * 程序的常量字段。
 * @author DwArFeng
 * @since 1.8
 */
public interface SchedulerAttrSet extends ProgramAttrSet {
	
	/**
	 * 返回程序的版本。
	 * <p> 请注意不要返回<code>null</code>。
	 * @return 程序的版本。
	 */
	public Version getVersion();
	
	/**
	 * 返回作者名称。
	 * <p> 请注意不要返回<code>null</code>。
	 * @return 作者的名称。
	 */
	public String getAuthor();
	
	/**
	 * 返回文本主键对应的文本。
	 * @param key 文本的主键。
	 * @return 主键对应的文本。
	 */
	public String getText(StringFieldKey key);
	
	/**
	 * 返回指定图片字段主键对应的图片。
	 * <p> 如果图片不存在，则返回默认的图片。
	 * @param key 图片字段主键。
	 * @return 主键对应的图片。
	 */
	public Image getImage(ImageKeys key);

}
