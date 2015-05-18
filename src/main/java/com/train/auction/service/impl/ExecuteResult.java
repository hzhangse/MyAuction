package com.train.auction.service.impl;

public class ExecuteResult {

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}



	public final static int succ = 1;
	public final static int failed = 0;

	private ExecuteResult(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	

	private String msg;
	private int code;

	public static ExecuteResult createFailedResult(String msg) {

		return new ExecuteResult(failed, msg);
	}

	public static ExecuteResult createSuccdResult(String msg) {

		return new ExecuteResult(succ, msg);
	}
}
