/**
 * 
 */
package com.gitrnd.qaproducer.qa.domain;

import java.sql.Timestamp;

public class QAProgress {

	/**
	 * tb_progress index(PK)
	 */
	Integer pIdx;
	/**
	 * tb_user index
	 */
	Integer uIdx;
	/**
	 * tb_file index
	 */
	Integer fIdx;
	/**
	 * qa 진행 상태
	 */
	Integer qaState;
	/**
	 * qa 시작시간
	 */
	Timestamp start_time;
	/**
	 * qa 종료시간
	 */
	Timestamp endTime;
	/**
	 * qa file명
	 */
	String originName;
	/**
	 * tb_qa_category index
	 */
	Integer qaCategory;
	/**
	 * qa type
	 */
	String qaType;
	/**
	 * file type
	 */
	String fileType;
	/**
	 * err file 저장 경로
	 */
	String errdirectory;
	/**
	 * err file name
	 */
	String errFileName;
	/**
	 * tb_preset index
	 */
	Integer prid;
	/**
	 * qa comment
	 */
	String comment;

	public Integer getpIdx() {
		return pIdx;
	}

	public void setpIdx(Integer pIdx) {
		this.pIdx = pIdx;
	}

	public Integer getuIdx() {
		return uIdx;
	}

	public void setuIdx(Integer uIdx) {
		this.uIdx = uIdx;
	}

	public Integer getfIdx() {
		return fIdx;
	}

	public void setfIdx(Integer fIdx) {
		this.fIdx = fIdx;
	}

	public Integer getQaState() {
		return qaState;
	}

	public void setQaState(Integer qaState) {
		this.qaState = qaState;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public Integer getQaCategory() {
		return qaCategory;
	}

	public void setQaCategory(Integer qaCategory) {
		this.qaCategory = qaCategory;
	}

	public String getQaType() {
		return qaType;
	}

	public void setQaType(String qaType) {
		this.qaType = qaType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getErrdirectory() {
		return errdirectory;
	}

	public void setErrdirectory(String errdirectory) {
		this.errdirectory = errdirectory;
	}

	public String getErrFileName() {
		return errFileName;
	}

	public void setErrFileName(String errFileName) {
		this.errFileName = errFileName;
	}

	public Integer getPrid() {
		return prid;
	}

	public void setPrid(Integer prid) {
		this.prid = prid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
