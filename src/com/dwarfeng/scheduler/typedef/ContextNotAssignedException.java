package com.dwarfeng.scheduler.typedef;

public class ContextNotAssignedException extends RuntimeException {

	private static final long serialVersionUID = -4034932512474795273L;

	public ContextNotAssignedException() {
	}

	public ContextNotAssignedException(String message) {
		super(message);
	}

	public ContextNotAssignedException(Throwable cause) {
		super(cause);
	}

	public ContextNotAssignedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContextNotAssignedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
