package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.io.Scpath;

/**
 * 附件异常。
 * <p> 该异常在附件被读取或保存到的时候发生。
 * @author DwArFeng
 * @since 1.8
 */
public final class AttachmentException extends Exception {

	private static final long serialVersionUID = 961446227167629923L;
	
	/**附件指示的对象。*/
	private final Object att;
	/**附件的路径*/
	private final Scpath scpath;
	
	/**
	 * 生成一个具有指定的附件，指定路径的附件异常。
	 * @param att 指定的附件。
	 * @param scpath 指定的路径。
	 */
	public AttachmentException(Object att,Scpath scpath) {
		this(att,scpath,null,null);
	}

	/**
	 * 生成一个具有指定的附件，指定的路径，指定的异常信息的附件异常。
	 * @param att 指定的附件。
	 * @param scpath 指定的路径。
	 * @param message 指定的异常信息。
	 */
	public AttachmentException(Object att,Scpath scpath,String message) {
		this(att,scpath,message,null);
	}

	/**
	 * 生成一个具有指定附件，指定的路径，指定的异常原因的附件异常。
	 * @param att 指定的附件。
	 * @param scpath 指定的路径。
	 * @param cause 指定的异常原因。
	 */
	public AttachmentException(Object att,Scpath scpath,Throwable cause) {
		this(att,scpath,null,cause);
	}

	/**
	 * 生成一个具有指定的附件，指定的路径，指定的异常信息，指定的异常原因的附件异常。
	 * @param att 指定的附件。
	 * @param scpath 指定的路径。
	 * @param message 指定的异常信息。
	 * @param cause 指定的异常原因。
	 */
	public AttachmentException(Object att,Scpath scpath,String message, Throwable cause) {
		super(message, cause);
		this.att = att;
		this.scpath = scpath;
	}
	
	/**
	 * 返回异常中的附件。
	 * @return 指定的附件。
	 */
	public Object getAttObject(){
		return this.att;
	}
	
	/**
	 * 返回异常中的路径。
	 * @return 异常中的路径。
	 */
	public Scpath getScpath(){
		return scpath;
	}


}
