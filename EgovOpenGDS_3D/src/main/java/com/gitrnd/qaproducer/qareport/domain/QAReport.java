/**
 * 
 */
package com.gitrnd.qaproducer.qareport.domain;


/**
 * @className FileValidateProgress.java
 * @description
 * @author DY.Oh
 * @since 2018. 2. 7. 오전 10:55:35
 */

public class QAReport {

	Integer r_idx;
	String layerId;
	Integer layerCount;
	Integer featureCount;
	Integer normalCount;
	Integer errCount;
	Integer exceptCount;
	String comment;
	Integer pIdx;
	
	public QAReport() {
		super();
	}
	public QAReport(Integer r_idx, String layerId, Integer layerCount, Integer featureCount, Integer normalCount,
			Integer errCount, Integer exceptCount, String comment, Integer pIdx) {
		super();
		this.r_idx = r_idx;
		this.layerId = layerId;
		this.layerCount = layerCount;
		this.featureCount = featureCount;
		this.normalCount = normalCount;
		this.errCount = errCount;
		this.exceptCount = exceptCount;
		this.comment = comment;
		this.pIdx = pIdx;
	}
	public Integer getR_idx() {
		return r_idx;
	}
	public void setR_idx(Integer r_idx) {
		this.r_idx = r_idx;
	}
	public String getLayerId() {
		return layerId;
	}
	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}
	public Integer getLayerCount() {
		return layerCount;
	}
	public void setLayerCount(Integer layerCount) {
		this.layerCount = layerCount;
	}
	public Integer getFeatureCount() {
		return featureCount;
	}
	public void setFeatureCount(Integer featureCount) {
		this.featureCount = featureCount;
	}
	public Integer getNormalCount() {
		return normalCount;
	}
	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}
	public Integer getErrCount() {
		return errCount;
	}
	public void setErrCount(Integer errCount) {
		this.errCount = errCount;
	}
	public Integer getExceptCount() {
		return exceptCount;
	}
	public void setExceptCount(Integer exceptCount) {
		this.exceptCount = exceptCount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getpIdx() {
		return pIdx;
	}
	public void setpIdx(Integer pIdx) {
		this.pIdx = pIdx;
	}

	
}
