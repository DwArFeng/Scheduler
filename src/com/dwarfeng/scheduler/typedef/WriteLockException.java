package com.dwarfeng.scheduler.typedef;

public class WriteLockException extends Exception {

	private static final long serialVersionUID = 5770561049928330837L;

	public WriteLockException() {
		// TODO Auto-generated constructor stub
	}

	public WriteLockException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WriteLockException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WriteLockException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WriteLockException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage(){
		String str = super.getMessage();
		return str == null || str.equals("") ? "Project was writelocked" : str;
	}
	
}
