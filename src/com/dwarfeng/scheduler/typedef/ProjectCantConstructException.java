package com.dwarfeng.scheduler.typedef;

public class ProjectCantConstructException extends Exception{

	private static final long serialVersionUID = 5432905315272104775L;

	public ProjectCantConstructException() {
	}

	public ProjectCantConstructException(String message) {
		super(message);
	}

	public ProjectCantConstructException(Throwable cause) {
		super(cause);
	}

	public ProjectCantConstructException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProjectCantConstructException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	@Override
	public String getMessage(){
		String str = super.getMessage();
		return str == null || str.equals("") ? "Project can't construct because the sch file may broken" : str;
	}
}
