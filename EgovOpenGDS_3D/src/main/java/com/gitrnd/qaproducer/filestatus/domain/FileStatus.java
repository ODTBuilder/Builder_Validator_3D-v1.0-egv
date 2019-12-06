package com.gitrnd.qaproducer.filestatus.domain;

import java.sql.Timestamp;

public class FileStatus {

	private int fid;
	private String location;
	private String fname;
	private Timestamp ctime;
	private int status;
	private int uidx;
	private String comment;

	public FileStatus() {
		super();
	}

	public FileStatus(int fid, String location, String fname, Timestamp ctime, int status, int uidx, String comment) {
		super();
		this.fid = fid;
		this.location = location;
		this.fname = fname;
		this.ctime = ctime;
		this.status = status;
		this.uidx = uidx;
		this.comment = comment;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUidx() {
		return uidx;
	}

	public void setUidx(int uidx) {
		this.uidx = uidx;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
