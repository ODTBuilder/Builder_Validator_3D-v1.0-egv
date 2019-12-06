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

import java.util.List;

/**
 * 
 * 
 * @author DY.Oh
 */
public class AttributeFigure {

	/**
	 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeFigure}
	 * 정의 순서에 따라 부여되는 index
	 */
	Long fIdx;
	/**
	 * 속성 key
	 */
	String key;
	/**
	 * 속성 value List
	 */
	List<Object> values;
	/**
	 * 속성 value (수치형 속성 value 검수할 때 사용)
	 */
	Double number;
	/**
	 * 속성 value 조건 (수치형 속성 value 검수할 때 사용)
	 */
	String condition;
	/**
	 * 속성 value 간격 (수치형 속성 value 검수할 때 사용)
	 */
	Double interval;

	public Long getfIdx() {
		return fIdx;
	}

	public void setfIdx(Long fIdx) {
		this.fIdx = fIdx;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
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
