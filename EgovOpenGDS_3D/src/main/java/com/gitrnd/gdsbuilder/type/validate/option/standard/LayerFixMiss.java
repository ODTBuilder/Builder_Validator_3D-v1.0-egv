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
package com.gitrnd.gdsbuilder.type.validate.option.standard;

import java.util.List;

/**
 * 특정 레이어의 Geometry 타입, 속성 컬럼 구조, 고정 속성값 검수 항목을 정의하는 클래스
 * 
 * @author DY.Oh
 *
 */
public class LayerFixMiss {

	/**
	 * 검수항목 이름
	 */
	String option;
	/**
	 * 레이어 ID
	 */
	String layerID;
	/**
	 * 레이어 Geometry 타입
	 */
	String geometry;
	/**
	 * 레이어 속성 컬럼 구조 및 고정 속성값
	 */
	List<FixedValue> fix;

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public List<FixedValue> getFix() {
		return fix;
	}

	public void setFix(List<FixedValue> fix) {
		this.fix = fix;
	}

}
