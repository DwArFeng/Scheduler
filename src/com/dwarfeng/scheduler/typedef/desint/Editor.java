package com.dwarfeng.scheduler.typedef.desint;


/**
 * 编辑接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface Editor {
	
	/**
	 * 获取其中的可编辑对象。
	 * @return 可编辑对象。
	 */
	public Editable getEditable();
	
	/**
	 * 对其中的可编辑对象进行读取操作。
	 * <p> 注意，编辑器是整个编辑流程的最顶层，
	 * 因此，对于在读取可编译对象时可能抛出的任何异常都要进行充分的处理。
	 */
	public void loadEditable();
	
	/**
	 * 对其中的可编辑对象进行存储操作。
	 * <p> 注意，编辑器是整个编辑流程的最顶层，
	 * 因此，对于在读存储编译对象时可能抛出的任何异常都要进行充分的处理。
	 */
	public void saveEditable();
}
