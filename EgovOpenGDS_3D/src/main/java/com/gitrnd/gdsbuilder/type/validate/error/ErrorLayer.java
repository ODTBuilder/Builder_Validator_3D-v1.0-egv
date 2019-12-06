/*
 *    OpenGDS/Builder
 *    http://git.co.kr
 *
 *    (C) 2014-2017, GeoSpatial Information Technology(GIT)
 *    
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

package com.gitrnd.gdsbuilder.type.validate.error;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer} 정보를 담고 있는 클래스
 * <p>
 * 다수의 {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorFeature}을
 * {@link java.util.List} 형태로 저장
 * <p>
 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer}는 단일
 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection} 또는
 * {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer} 검수 결과로 생성됨.
 * 
 * @author DY.Oh
 */
public class ErrorLayer {

	/**
	 * 오류 객체가 포함된 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer}의 ID
	 */
	String layerID;
	/**
	 * 오류 객체가 포함된
	 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}의 ID
	 */
	String collectionName;
	/**
	 * 오류 객체가 포함된
	 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}의 Type
	 */
	String collectionType;
	/**
	 * 오류 객체 리스트
	 */
	List<ErrorFeature> errFeatureList;
	/**
	 * 검수를 수행한 모든 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer}의 수
	 */
	int layerCount;
	/**
	 * 검수를 수행한 모든 {@link com.gitrnd.gdsbuilder.type.dt.feature.DTFeature}의 수
	 */
	int featureCount;
	/**
	 * 검수를 수행한 {@link com.gitrnd.gdsbuilder.type.dt.feature.DTFeature} 중 오류 객체의 수
	 */
	int errCount;
	/**
	 * 검수를 수행한 {@link com.gitrnd.gdsbuilder.type.dt.feature.DTFeature} 중 오류가 아닌 객체의
	 * 수
	 */
	int nomalCount;
	/**
	 * 검수를 수행한 {@link com.gitrnd.gdsbuilder.type.dt.feature.DTFeature} 중 예외 객체의 수
	 */
	int exceptCount;
	/**
	 * 오류 세부 사항
	 */
	String comment;

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public List<ErrorFeature> getErrFeatureList() {
		return errFeatureList;
	}

	public void setErrFeatureList(List<ErrorFeature> errFeatureList) {
		this.errFeatureList = errFeatureList;
	}

	public int getLayerCount() {
		return layerCount;
	}

	public void setLayerCount(int layerCount) {
		this.layerCount = layerCount;
	}

	public int getFeatureCount() {
		return featureCount;
	}

	public void setFeatureCount(int featureCount) {
		this.featureCount = featureCount;
	}

	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	public int getNomalCount() {
		return nomalCount;
	}

	public void setNomalCount(int nomalCount) {
		this.nomalCount = nomalCount;
	}

	public int getExceptCount() {
		return exceptCount;
	}

	public void setExceptCount(int exceptCount) {
		this.exceptCount = exceptCount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ErrorLayer() {
		super();
		this.layerID = "";
		this.collectionName = "";
		this.errFeatureList = new ArrayList<ErrorFeature>();
		this.collectionType = "";
	}

	/**
	 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer}에 오류 객체에 대한 검수
	 * 결과인 {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorFeature} 추가
	 * 
	 * @param errFeature
	 *            오류 객체에 대한 검수 결과
	 *            {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorFeature}
	 * 
	 * @author DY.Oh
	 */
	public void addErrorFeature(ErrorFeature errFeature) {
		this.errFeatureList.add(errFeature);
	}

	/**
	 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer}에 다수 오류 객체에 대한
	 * 검수 결과인 {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorFeature} List
	 * 추가
	 * 
	 * @param errFeatures
	 *            다수 오류 객체에 대한 검수 결과인
	 *            {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorFeature}
	 *            List
	 * 
	 * @author DY.Oh
	 */
	public void addErrorFeatureList(List<ErrorFeature> errFeatures) {
		this.errFeatureList.addAll(errFeatures);
	}

	/**
	 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer}에 또 다른
	 * {@link com.gitrnd.gdsbuilder.type.validate.error.ErrorLayer}를 병합.
	 * 
	 * @param errLayer
	 *            단일
	 *            {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}
	 *            또는 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer} 검수 결과
	 * 
	 * @author DY.Oh
	 */
	public void mergeErrorLayer(ErrorLayer errLayer) {
		this.errFeatureList.addAll(errLayer.getErrFeatureList());
	}

}
