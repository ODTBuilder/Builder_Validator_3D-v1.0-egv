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

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeMiss} 또는
 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.GraphicMiss} 검수 항목 정의
 * 시 검수 대상 레이어의 속성 key, value 정보를 저장하는 클래스
 * 
 * @author DY.Oh
 *
 */
public class OptionFigure {

	/**
	 * 검수 대상 레이어 ID
	 */
	String layerID;
	/**
	 * 속성 key, value 정보 List
	 */
	List<AttributeFigure> figure;

	public String getLayerID() {
		return layerID;
	}

	public void setLayerID(String layerID) {
		this.layerID = layerID;
	}

	public List<AttributeFigure> getFigure() {
		return figure;
	}

	public void setFigure(List<AttributeFigure> figure) {
		this.figure = figure;
	}

	/**
	 * fidx에 해당하는 List<AttributeFigure> 반환
	 * <p>
	 * {@link com.gitrnd.gdsbuilder.type.validate.option.specific.AttributeFigure}
	 * 정의 시 순서에 따라 index를 부여하며 해당 index로 List<AttributeFigure>를 검색할 수 있음.
	 * 
	 * @param fidx
	 *            검수 옵션 index
	 * @return List<AttributeFigure> fidx에 해당하는 List<AttributeFigure>
	 * 
	 * @author DY.Oh
	 */
	public List<AttributeFigure> getFilterFigure(int fidx) {

		List<AttributeFigure> filterFigures = new ArrayList<>();
		for (AttributeFigure attrFigure : figure) {
			Long fidxL = attrFigure.getfIdx();
			if (fidxL != null) {
				if (fidx == fidxL.intValue()) {
					filterFigures.add(attrFigure);
				}
			}
		}
		if (filterFigures.size() > 0) {
			return filterFigures;
		} else {
			return null;
		}
	}
}
