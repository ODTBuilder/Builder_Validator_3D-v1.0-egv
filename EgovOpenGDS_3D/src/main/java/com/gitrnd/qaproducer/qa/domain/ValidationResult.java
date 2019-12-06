package com.gitrnd.qaproducer.qa.domain;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class ValidationResult extends JSONObject {
	
	public int pidx;
	public int uidx;
	public int fidx;
	public String zipName;
	public Timestamp createTime;
	public Timestamp endTime;
	public String qaType;
	public String format;
	public int state;
	public String errName;
	public String errFileDir;
	public String comment;

	public int getPidx() {
		return pidx;
	}

	public void setPidx(int pidx) {
		this.pidx = pidx;
	}

	public int getUidx() {
		return uidx;
	}

	public void setUidx(int uidx) {
		this.uidx = uidx;
	}

	public int getFidx() {
		return fidx;
	}

	public void setFidx(int fidx) {
		this.fidx = fidx;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getQaType() {
		return qaType;
	}

	public void setQaType(String qaType) {
		this.qaType = qaType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public String getErrFileDir() {
		return errFileDir;
	}

	public void setErrFileDir(String errFileDir) {
		this.errFileDir = errFileDir;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
