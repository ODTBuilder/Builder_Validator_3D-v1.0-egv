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
package com.gitrnd.gdsbuilder.type.dt.feature;

import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeFilter;

/**
 * {@link com.gitrnd.gdsbuilder.type.dt.feature.DTFeature}정보를 저장하는 클래스.
 * <p>
 * 상위 {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}의 ID,
 * {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer}의 ID 및 Geometry/Attribute 값을
 * 저장하는 {@link org.opengis.feature.simple.SimpleFeature}, 속성 필터링을 위한
 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeFilter}
 * List로 구성됨.
 * 
 * @author DY.Oh
 */
public class DTFeature {

	/**
	 * 상위 {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}의 ID
	 */
	String typeName;
	/**
	 * 상위 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer}의 ID
	 */
	String layerID;
	/**
	 * Geometry/Attribute 값을 저장하는
	 * {@link org.opengis.feature.simple.SimpleFeature}
	 */
	SimpleFeature simefeature;
	/**
	 * 속성 필터링을 위한
	 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeFilter}
	 * List
	 */
	List<AttributeFilter> filter;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public SimpleFeature getSimefeature() {
		return simefeature;
	}

	public void setSimefeature(SimpleFeature simefeature) {
		this.simefeature = simefeature;
	}

	public List<AttributeFilter> getFilter() {
		return filter;
	}

	public void setFilter(List<AttributeFilter> filter) {
		this.filter = filter;
	}

	public DTFeature(String layerID, SimpleFeature simefeature, List<AttributeFilter> filter) {
		this.layerID = layerID;
		this.simefeature = simefeature;
		this.filter = filter;
	}

}
