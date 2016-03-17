package com.simbest.cores.exceptions;

import org.apache.shiro.authz.UnauthorizedException;

/**
 * 流程审批异常
 * @author lishuyi
 */
public class UnExpectedAuditException extends UnauthorizedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607063540894022535L;
	private String errorCode;
	private String errorMessage;

	/**
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public UnExpectedAuditException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 * @param errorCode
	 * @param errorMessage
	 * @param e
	 */
	public UnExpectedAuditException(String errorCode, String errorMessage, Throwable e) {
		super(errorMessage, e);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
		
}
