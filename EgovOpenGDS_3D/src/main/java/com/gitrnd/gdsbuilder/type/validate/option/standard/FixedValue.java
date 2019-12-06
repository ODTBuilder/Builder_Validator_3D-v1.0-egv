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
 * 특정 레이어의 속성 컬럼 구조, 고정 속성값 검수 항목을 정의하는 클래스
 * 
 * @author DY.Oh
 */
public class FixedValue {

	/**
	 * 속성 컬럼명
	 */
	String key;
	/**
	 * 속성 타입(ex. String, Integer, Double..)
	 */
	String type;
	/**
	 * 속성값 Null 허용 여부
	 */
	boolean isnull;
	/**
	 * 속성값 길이
	 */
	Long length;
	/**
	 * 고정 속성값 List (해당 속성값만 가질 수 있음)
	 */
	List<Object> values;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIsnull() {
		return isnull;
	}

	public void setIsnull(boolean isnull) {
		this.isnull = isnull;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

}
