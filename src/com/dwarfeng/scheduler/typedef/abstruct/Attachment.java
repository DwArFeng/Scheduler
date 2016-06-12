package com.dwarfeng.scheduler.typedef.abstruct;

/**
 * 附件接口。实现该接口的对象是工程文件的一部分，附件在占有
 * 工程结构的同时，还与工程中的某个文件相连接。
 * @author DwArFeng
 * @since 1.8
 */
public interface Attachment extends ObjectInProject,Scpathable{
	
	/**
	 * 读取附件指向的文档。
	 */
	public void load() throws Exception;
	
	/**
	 * 保存附件指向的文档。
	 * @throws Exception 出现异常。
	 */
	public void save() throws Exception;
	
	/**
	 * 释放附件文档。
	 */
	public void release();
	
	/**
	 * 获得目标对象。
	 * <p> 该对象是与附件文件直接关联的对象，请保证该方法永远不返回空值。
	 * <p> 如果目标对象为空，则返回{@linkplain Attachment#createDefaultObject()}中的对象。
	 * @return 目标对象。
	 */
	public Object getAttachObject();
	
	/**
	 * 设置目标对象为指定的对象。
	 * @param target 指定的对象。
	 */
	public void setAttachObject(Object obj);
	/**
	 * 创建默认的附件对象。
	 * @return 默认的附件对象。
	 */
	public Object createDefaultObject();
}
