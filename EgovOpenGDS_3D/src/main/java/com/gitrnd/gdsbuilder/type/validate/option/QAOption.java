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
package com.gitrnd.gdsbuilder.type.validate.option;

import java.util.List;

import com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeMiss;
import com.gitrnd.gdsbuilder.type.validate.option.specific.CloseMiss;
import com.gitrnd.gdsbuilder.type.validate.option.specific.GraphicMiss;
import com.gitrnd.gdsbuilder.type.validate.option.standard.LayerFixMiss;

/**
 * 검수 옵션 정의 클래스
 * <p>
 * 특정 Name에 해당하는 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayerList}가 수행할 검수
 * 옵션에 대한 정보를 저장
 * <p>
 * 레이어 Geometry 타입, 속성 컬럼 구조 검수 항목인
 * {@link com.gitrnd.gdsbuilder.type.validate.option.standard.LayerFixMiss}, 레이어 내
 * 객체의 속성 값 검수 항목인
 * {@link com.gitrnd.gdsbuilder.type.validate.option.standard.AttributeMiss}, 레이어 내
 * 객체의 Geometry 검수 항목인
 * {@link com.gitrnd.gdsbuilder.type.validate.option.standard.GraphicMiss}, 인접 검수
 * 영역의 레이어 간 검수 항목인
 * {@link com.gitrnd.gdsbuilder.type.validate.option.standard.CloseMiss} 각각을 List
 * 형태로 저장
 * 
 * @author DY.Oh
 *
 */
public class QAOption {

	/**
	 * {@link com.gitrnd.gdsbuilder.type.validate.option.QAOption} Name
	 * <p>
	 * {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayerList}의 분류 명
	 */
	String name;
	/**
	 * 레이어 Geometry 타입, 속성 컬럼 구조 검수 항목 List
	 */
	List<LayerFixMiss> layerMissOptions;
	/**
	 * 레이어 내 객체의 속성 값 검수 항목 List
	 */
	List<AttributeMiss> attributeMissOptions;
	/**
	 * 레이어 내 객체의 Geometry 검수 항목 List
	 */
	List<GraphicMiss> graphicMissOptions;
	/**
	 * 인접 검수 영역의 레이어 간 검수 항목 List
	 */
	List<CloseMiss> closeMissOptions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LayerFixMiss> getLayerMissOptions() {
		return layerMissOptions;
	}

	public void setLayerMissOptions(List<LayerFixMiss> layerMissOptions) {
		this.layerMissOptions = layerMissOptions;
	}

	public List<AttributeMiss> getAttributeMissOptions() {
		return attributeMissOptions;
	}

	public void setAttributeMissOptions(List<AttributeMiss> attributeMissOptions) {
		this.attributeMissOptions = attributeMissOptions;
	}

	public List<GraphicMiss> getGraphicMissOptions() {
		return graphicMissOptions;
	}

	public void setGraphicMissOptions(List<GraphicMiss> graphicMissOptions) {
		this.graphicMissOptions = graphicMissOptions;
	}

	public List<CloseMiss> getCloseMissOptions() {
		return closeMissOptions;
	}

	public void setCloseMissOptions(List<CloseMiss> closeMissOptions) {
		this.closeMissOptions = closeMissOptions;
	}

}
