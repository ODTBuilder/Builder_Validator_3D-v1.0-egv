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
package com.gitrnd.gdsbuilder.type.validate.option.specific;

/**
 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeMiss} 또는
 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.GraphicMiss} 검수 항목 정의
 * 시 검수 대상 레이어의 허용 오차범위, 수치 조건 등의 수치 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 *
 */
public class OptionTolerance {

	/**
	 * 검수 대상 레이어 ID
	 */
	String layerID;
	/**
	 * 수치값
	 */
	Double value;
	/**
	 * 수치조건 (under, over ...)
	 */
	String condition;
	/**
	 * 수치값의 간격 (ex. interval : 3 -> value : 3, 6, 9, 12....)
	 */
	Double interval;

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Double getInterval() {
		return interval;
	}

	public void setInterval(Double interval) {
		this.interval = interval;
	}

}
