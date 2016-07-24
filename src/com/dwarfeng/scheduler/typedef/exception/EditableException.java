package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * 可编辑对象异常。
 * <p> 该异常通常发生在对可编辑对象进行编辑时，如开始编辑时的加载失败，编辑时进行保存的保存失败等。
 * @author DwArFeng
 * @since 1.8
 */
public class EditableException extends Exception {
	
	private static final long serialVersionUID = 4558641177821370674L;
	
	/**异常的可编辑对象源*/
	private final Editable editable;

	/**
	 * 生成默认的可编辑对象异常。
	 * @param editable 指定的异常源。
	 */
	public EditableException(Editable editable) {
		this(editable,null);
	}

	/**
	 * 生成具有指定消息的可编辑对象异常。
	 * @param editable 指定的异常源。
	 * @param message 指定的消息。
	 */
	public EditableException(Editable editable,String message) {
		super(message);
		this.editable = editable;
	}

	/**
	 * 返回该异常的异常源。
	 * @return 该异常的异常源。
	 */
	public Editable getEditable() {
		return editable;
	}

}
