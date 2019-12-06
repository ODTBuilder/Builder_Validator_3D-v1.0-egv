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

package com.gitrnd.gdsbuilder.type.validate.layer;

import java.util.List;

import com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection;
import com.gitrnd.gdsbuilder.type.dt.layer.DTLayer;
import com.gitrnd.gdsbuilder.type.validate.option.QAOption;

/**
 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}의 검수 옵션
 * <p>
 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}에 포함된 특정
 * {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayerList}에 대하여 수행할 검수 항목 및 항목 별
 * 수치값 정보를 {@link com.gitrnd.gdsbuilder.type.validate.option.QAOption}에 저장함.
 * 
 * @author DY.Oh
 */

public class QALayerType {

	/**
	 * QALayerType명
	 */
	String name;
	/**
	 * QALayerType에 해당하는 LayerID List
	 */
	List<String> layerIDList;
	/**
	 * QALayerType에 해당하는 LayerID List의 모든 DTLayer가 수행할 검수 옵션
	 */
	QAOption option;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLayerIDList() {
		return layerIDList;
	}

	public void setLayerIDList(List<String> layerIDList) {
		this.layerIDList = layerIDList;
	}

	public QAOption getOption() {
		return option;
	}

	public void setOption(QAOption option) {
		this.option = option;
	}

	/**
	 * {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection} 중
	 * layerID에 해당하는 {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer} 반환
	 * 
	 * @param layerID
	 *            {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer} layerID
	 * @param colleciton
	 *            {@link com.gitrnd.gdsbuilder.type.dt.collection.DTLayerCollection}
	 * @return DTLayer layerID에 해당하는
	 *         {@link com.gitrnd.gdsbuilder.type.dt.layer.DTLayer}
	 * 
	 * @author GIT
	 */
	public DTLayer getTypeLayer(String layerID, DTLayerCollection colleciton) {

		DTLayer dtLayer = null;
		for (int i = 0; i < this.layerIDList.size(); i++) {
			String id = this.layerIDList.get(i);
			if (id.equals(layerID)) {
				dtLayer = colleciton.getLayer(id);
			}
		}
		if (dtLayer != null) {
			return dtLayer;
		} else {
			return null;
		}
	}
}
