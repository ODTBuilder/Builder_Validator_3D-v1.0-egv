/**
 * 
 */
package com.gitrnd.qaproducer.qareport.details.domain;

/**
 * @className FileValidateProgress.java
 * @description
 * @author DY.Oh
 * @since 2018. 2. 7. 오전 10:55:35
 */

public class QADetailReport {

	public QADetailReport() {
		super();
	}

	public QADetailReport(Integer rd_idx, String refLayerId, String featureId, String refFeatureId, String errType,
			String errName, String errPoint, String comment, Integer rIdx) {
		super();
		this.rd_idx = rd_idx;
		this.refLayerId = refLayerId;
		this.featureId = featureId;
		this.refFeatureId = refFeatureId;
		this.errType = errType;
		this.errName = errName;
		this.errPoint = errPoint;
		this.comment = comment;
		this.rIdx = rIdx;
	}

	Integer rd_idx;
	String refLayerId;
	String featureId;
	String refFeatureId;
	String errType;
	String errName;
	String errPoint;
	String comment;
	Integer rIdx;

	/**
	 * @param refLayerId
	 * @param featureId
	 * @param reffeatureId
	 * @param errType
	 * @param errName
	 * @param errPoint
	 * @param comment
	 * @param rIdx
	 */
	public QADetailReport(String refLayerId, String featureId, String reffeatureId, String errType, String errName,
			String errPoint, String comment, Integer rIdx) {
		super();
		this.refLayerId = refLayerId;
		this.featureId = featureId;
		this.refFeatureId = reffeatureId;
		this.errType = errType;
		this.errName = errName;
		this.errPoint = errPoint;
		this.comment = comment;
		this.rIdx = rIdx;
	}

	public Integer getRd_idx() {
		return rd_idx;
	}

	public void setRd_idx(Integer rd_idx) {
		this.rd_idx = rd_idx;
	}

	public String getRefLayerId() {
		return refLayerId;
	}

	public void setRefLayerId(String refLayerId) {
		this.refLayerId = refLayerId;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public String getRefFeatureId() {
		return refFeatureId;
	}

	public void setRefFeatureId(String refFeatureId) {
		this.refFeatureId = refFeatureId;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getErrName() {
		return errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public String getErrPoint() {
		return errPoint;
	}

	public void setErrPoint(String errPoint) {
		this.errPoint = errPoint;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getrIdx() {
		return rIdx;
	}

	public void setrIdx(Integer rIdx) {
		this.rIdx = rIdx;
	}

	
}


