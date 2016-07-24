package com.dwarfeng.scheduler.typedef.exception;

import com.dwarfeng.scheduler.module.Scpath;

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
	 * 
	 * @param att
	 * @param scpath
	 */
	public AttachmentException(Object att,Scpath scpath) {
		this(att,scpath,null,null);
	}

	/**
	 * 
	 * @param att
	 * @param scpath
	 * @param message
	 */
	public AttachmentException(Object att,Scpath scpath,String message) {
		this(att,scpath,message,null);
	}

	/**
	 * 
	 * @param att
	 * @param scpath
	 * @param cause
	 */
	public AttachmentException(Object att,Scpath scpath,Throwable cause) {
		this(att,scpath,null,cause);
	}

	/**
	 * 
	 * @param att
	 * @param scpath
	 * @param message
	 * @param cause
	 */
	public AttachmentException(Object att,Scpath scpath,String message, Throwable cause) {
		super(message, cause);
		this.att = att;
		this.scpath = scpath;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getAttObject(){
		return this.att;
	}
	
	/**
	 * 
	 * @return
	 */
	public Scpath getScpath(){
		return scpath;
	}


}
