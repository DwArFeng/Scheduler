package com.dwarfeng.scheduler.module.project.abstruct;

import com.dwarfeng.scheduler.module.PScpathable;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;

/**
 * 附件接口。
 * <p>此接口实现该接口的对象是工程文件的一部分，附件在占有工程结构的同时，还与工程中的某个文件相连接。
 * <br> 该接口是个泛型接口，其中的泛型代表着附件指示的文件最终能以什么类被读取以及以什么类被保存。
 * @author DwArFeng
 * @since 1.8
 */
interface Attachment<T> extends PScpathable{
	
	/**
	 * 从附件中读取文件，并且以泛型指示的类返回。
	 * @return 去读的文件的对象形式。
	 * @throws AttachmentException 附件读取失败异常。
	 */
	public T load() throws AttachmentException;
	
	/**
	 * 将指定的对象保存在附件中，一般来说，这会覆盖原有的附件。
	 * @param obj 指定的对象。
	 * @throws AttachmentException 附件保存失败异常。 
	 */
	public void save(T obj) throws AttachmentException;
	
	/**
	 * 创建默认的附件对象。
	 * @return 默认的附件对象。
	 */
	public T createDefaultObject();
}
