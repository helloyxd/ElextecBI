package com.jybi.report.model;

import java.io.Serializable;

/**
 * 接口返回固定格式实体类(图表)
 */
public class ReportReturnT<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;
	public static final int NO_LOGIN = 304;
	public static final ReportReturnT<String> SUCCESS = new ReportReturnT<String>(null);
	public static final ReportReturnT<String> FAIL = new ReportReturnT<String>(FAIL_CODE, null);

	private int code;
	private String title;
	private String colConf;
	private String msg;
	private T content;

	public ReportReturnT(){}
	public ReportReturnT(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ReportReturnT(T content) {
		this.code = SUCCESS_CODE;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColConf() {
		return colConf;
	}

	public void setColConf(String colConf) {
		this.colConf = colConf;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ReturnT [code=" + code + ", msg=" + msg + ", content=" + content + "]";
	}

}
