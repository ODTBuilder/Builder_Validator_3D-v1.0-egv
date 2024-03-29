var gb;
if (!gb)
	gb = {};
if (!gb.interaction)
	gb.interaction = {};

/**
 * @classdesc 피처에 대해 Scale, Rotate, Flip 편집을 수행할 수 있는 Interaction. ol.Map에 추가하여
 *            기능을 사용한다.
 * @class gb.interaction.MultiTransform
 * @memberof gb.interaction
 * @constructor
 * @param {Object}
 *            opt_options - gb.interaction.MultiTransform 생성 옵션
 * @param {ol.Collection.
 *            <ol.Feature>} opt_options.features - MultiTransform을 적용시킬 feature
 *            객체 집합
 * @author KIM HOCHUL
 * @date 2019. 03. 25
 * @version 0.01
 */
gb.interaction.MultiTransform = function(opt_options) {
	ol.interaction.Pointer.call(this, {
		handleDownEvent : gb.interaction.MultiTransform.prototype.handleDownEvent,
		handleDragEvent : gb.interaction.MultiTransform.prototype.handleDragEvent,
		handleMoveEvent : gb.interaction.MultiTransform.prototype.handleMoveEvent,
		handleUpEvent : gb.interaction.MultiTransform.prototype.handleUpEvent
	});

	/**
	 * 현재 커서의 위치를 저장
	 * 
	 * @type {Array.<number>}
	 * @private
	 */
	this.cursorCoordinate_ = null;

	/**
	 * 이전 커서의 위치를 저장
	 * 
	 * @type {Array.<number>}
	 * @private
	 */
	this.prevCursor_ = null;

	/**
	 * 수행할 작업을 저장 ('rotate', 'scale', 'filp')
	 * 
	 * @type {string}
	 * @private
	 */
	this.task_ = null;

	/**
	 * true 일 때 drag event로 feature scale 실행
	 * 
	 * @type {boolean}
	 * @private
	 */
	this.scale_ = false;

	/**
	 * true 일 때 drag event로 feature rotate 실행
	 * 
	 * @type {boolean}
	 * @private
	 */
	this.rotate_ = false;

	/**
	 * rotate, scale 함수 parameter 값을 위한 feature의 중점좌표
	 * 
	 * @type {Array.<number>}
	 * @private
	 */
	this.flatInteriorPoint_ = null;

	/**
	 * 회전각
	 * 
	 * @type {number}
	 * @private
	 */
	this.angle_ = 0;

	/**
	 * 크기 배율
	 * 
	 * @type {number}
	 * @private
	 */
	this.scaleRatio_ = 1;
	this.tempRatio = undefined;

	var options = opt_options ? opt_options : {};
	this.features_ = options.features;
};
ol.inherits(gb.interaction.MultiTransform, ol.interaction.Pointer);

/**
 * map 객체에 있는 interaction 중에 ol.interaction.Select 객체를 찾아 비활성화 시키거나 활성화 시킴
 * MultiTransform 버튼을 클릭하였을때 객체 선택이 풀리는 경우를 방지하기 위해 사용
 * 
 * @method gb.interaction.MultiTransform#setActiveSelect_
 * @function
 * @param {boolean}
 *            bool - true일 시 Select Interaction을 활성화
 */
gb.interaction.MultiTransform.prototype.setActiveSelect_ = function(bool) {
	var map = this.getMap();
	map.getInteractions().forEach(function(interaction) {
		if (interaction instanceof ol.interaction.Select) {
			interaction.setActive(bool);
		}
	});
}

/**
 * ol.interaction.Select의 active상태를 반환
 * 
 * @method gb.interaction.MultiTransform#getActiveSelect_
 * @function
 * @return {boolean}
 */
gb.interaction.MultiTransform.prototype.getActiveSelect_ = function() {
	var map = this.getMap();
	var bool = null;
	map.getInteractions().forEach(function(interaction) {
		if (interaction instanceof ol.interaction.Select) {
			bool = interaction.getActive();
		}
	});
	return bool;
}

/**
 * down이벤트 발생시 우선적으로 select interaction의 active를 확인하고 false일 경우 true로 값을 설정해준다.
 * 선택된 피쳐가 있고 cursor가 버튼 위에 위치해 있다면 작업을 수행한다. 수행해야할 작업이 rotate라면 {this.rotate_}를
 * true로 설정하고 scale이면 {this.scale_}을 true로 설정한다. flip일 경우의 이벤트는 select
 * interaction의 click 이벤트와 겹치기때문에 동시 실행되어 예기치 못한 상황이 발생할 수 있으므로 select active를
 * false로 설정한다.
 * 
 * @method gb.interaction.MultiTransform#handleDownEvent
 * @function
 * @param {ol.events.Event}
 *            evt - ol.Map 이벤트 객체
 * @return {boolean} true 반환시 drag squence 시작
 */
gb.interaction.MultiTransform.prototype.handleDownEvent = function(evt) {
	var feature = this.features_.item(0);

	if (!this.getActiveSelect_()) {
		this.setActiveSelect_(true);
	}

	if (feature) {
		var map = evt.map;
		var element = evt.map.getTargetElement();
		var geometry = feature.getGeometry();
		var extent = geometry.getExtent();
		var x = extent[0] + (extent[2] - extent[0]) / 2;
		var y = extent[1] + (extent[3] - extent[1]) / 2;

		if (element.style.cursor !== '' && this.task_) {
			switch (this.task_) {
			case 'rotate':
				this.rotate_ = true;
				break;
			case 'scaleW':
			case 'scaleE':
				this.scale_ = true;
				break;
			default:
				if (this.getActiveSelect_()) {
					this.setActiveSelect_(false);
				}
			}
			this.flatInteriorPoint_ = [ x, y ];
		}
	}
	// this.dispatchEvent(new
	// gb.interaction.MultiTransform.Event(gb.interaction.MultiTransformEventType.TRANSFORMSTART,
	// feature, evt));
	// this.dispatchEvent(gb.interaction.MultiTransformEventType.TRANSFORMSTART);
	this.dispatchEvent(new gb.interaction.MultiTransform.Event.TransformEvent(gb.interaction.MultiTransformEventType.TRANSFORMSTART, this.angle_, this.scaleRatio_));
	return (!!feature && !!this.task_);
};

/**
 * rotate 또는 scale을 수행한다. Click & Drag 방식의 이벤트 처리 함수.
 * 
 * @method gb.interaction.MultiTransform#handleDragEvent
 * @function
 * @param {ol.events.Event}
 *            evt - ol.Map 이벤트 객체
 */
gb.interaction.MultiTransform.prototype.handleDragEvent = function(evt) {

	var feature = this.features_.item(0);

	if (this.rotate_ === true) {
		var prev = this.prevCursor_;
		var current = evt.coordinate;
		var rad = this.rotateAlgorithm_(prev, current);
		// shift키 누른채로 mouse move시 rotate 회전 속도가 절반으로 줄어듬
		if (evt.originalEvent.shiftKey === true) {
			feature.getGeometry().rotate(rad / 2, this.flatInteriorPoint_);
			this.angle_ += rad / 2;
		} else {
			feature.getGeometry().rotate(rad, this.flatInteriorPoint_);
			this.angle_ += rad;
		}
		console.log(this.angle_);
	}

	if (this.scale_ === true) {
		var cursorPoint = evt.coordinate;
		var magni = this.scaleAlgorithm_(feature, cursorPoint);
		if (magni[0] > magni[1]) {
			feature.getGeometry().scale(magni[0], magni[0], this.flatInteriorPoint_);
			this.scaleRatio_ = this.scaleRatio_ * magni[0];
		} else {
			feature.getGeometry().scale(magni[1], magni[1], this.flatInteriorPoint_);
			this.scaleRatio_ = this.scaleRatio_ * magni[1];
		}

		this.angle_ = 0;
	}
	// this.dispatchEvent(new
	// gb.interaction.MultiTransform.Event(gb.interaction.MultiTransformEventType.TRANSFORMING,
	// feature, evt));
	// this.dispatchEvent(gb.interaction.MultiTransformEventType.TRANSFORMING);
	this.dispatchEvent(new gb.interaction.MultiTransform.Event.TransformEvent(gb.interaction.MultiTransformEventType.TRANSFORMING, this.angle_, this.scaleRatio_));
	this.prevCursor_ = evt.coordinate;
};

/**
 * 커서 이동 이벤트 처리 함수.drag 실행 중에는 실행되지 않는다. {this.selectTask_} 함수를 통해
 * MultiTransform 버튼의 위치를 인식하고 커서가 버튼위에 있을때 {this.task_}의 값과 마우스 커서 스타일 값을 변경한다.
 * 
 * @method gb.interaction.MultiTransform#handleMoveEvent
 * @function
 * @param {ol.events.Event}
 *            evt - ol.Map 이벤트 객체
 */
gb.interaction.MultiTransform.prototype.handleMoveEvent = function(evt) {
	if (!evt.dragging) {
		var map = evt.map
		var feature = this.features_.item(0);
		var element = evt.map.getTargetElement();
		var task = null;

		if (!!feature) {
			task = this.selectTask_(map, feature, evt.pixel);

			if (!!task) {
				switch (task) {
				case 'rotate':
					element.style.cursor = 'pointer';
					break;
				case 'scaleW':
					element.style.cursor = 'nw-resize';
					break;
				case 'scaleE':
					element.style.cursor = 'ne-resize';
					break;
				default:
					element.style.cursor = 'pointer';
				}
				this.task_ = task;
			} else {
				if (element.style.cursor !== '') {
					element.style.cursor = '';
				}
				if (this.task_) {
					this.task_ = null;
				}
			}
		} else {
			if (element.style.cursor !== '') {
				element.style.cursor = '';
			}
			if (this.task_) {
				this.task_ = null;
			}
		}
		this.cursorCoordinate_ = evt.coordinate;
	}
};

/**
 * {this.task_}가 flip 작업을 위한 값일때 flip을 실행한다. {this.rotate_}와 {this.scale_}의 값이
 * true 일때 해당작업들에서 설정되었던 멤버변수들을 모두 초기화시킨다.
 * 
 * @method gb.interaction.MultiTransform#handleUpEvent
 * @function
 * @param {ol.events.Event}
 *            evt - ol.Map 이벤트 객체
 * @return {boolean}
 */
gb.interaction.MultiTransform.prototype.handleUpEvent = function(evt) {
	var feature = this.features_.item(0);
	var task = null;
	var map = evt.map;
	var element = evt.map.getTargetElement();

	if (feature) {
		if (element.style.cursor !== '' && this.task_ !== 'rotate' && !this.task_.match(/^scale/i)) {
			this.flipAlgorithm_(feature, this.task_);

			task = this.selectTask_(map, feature, evt.pixel);

			if (!!task) {
				switch (task) {
				case 'rotate':
					element.style.cursor = 'pointer';
					break;
				case 'scaleW':
					element.style.cursor = 'nw-resize';
					break;
				case 'scaleE':
					element.style.cursor = 'ne-resize';
					break;
				default:
					element.style.cursor = 'pointer';
				}
				this.task_ = task;
			} else {
				if (element.style.cursor !== '') {
					element.style.cursor = '';
				}
				if (this.task_) {
					this.task_ = null;
				}
			}
		}
	}

	if (this.rotate_ || this.scale_) {
		this.rotate_ = false;
		this.scale_ = false;
		this.prevCursor_ = null;
		this.flatInteriorPoint_ = null;
		element.style.cursor = '';
	}
	// this.dispatchEvent(new
	// gb.interaction.MultiTransform.Event(gb.interaction.MultiTransformEventType.TRANSFORMEND,
	// feature, evt));
	this.dispatchEvent(new gb.interaction.MultiTransform.Event.TransformEvent(gb.interaction.MultiTransformEventType.TRANSFORMEND, this.angle_, this.scaleRatio_));
	this.angle_ = 0;
	this.scaleRatio_ = 1;
	return false;
};

/**
 * 'flip', 'rotate', 'scale' 기능을 수행할 수 있는 마우스 이벤트 영역을 {ol.style.Style}객체로 그려낸다.
 * 
 * @method gb.interaction.MultiTransform#drawMbr
 * @function
 * @param {ol.render.Event}
 *            evt - ol.render 이벤트 객체
 */
gb.interaction.MultiTransform.prototype.drawMbr = function(evt) {

	var map = this.getMap();

	if (this.features_.item(0) !== undefined) {

		var mbr = null;
		var line = null;
		var circle = null;

		var triangle = [];
		var square = [];
		var point = [];

		var features = [];

		var strokes = {
			'line' : new ol.style.Stroke({
				color : 'rgba(152,152,152,0.6)',
				width : 3,
				lineDash : [ 1, 4 ]
			}),
			'default' : new ol.style.Stroke({
				color : 'rgba(152,152,152,0.8)',
				width : 1.5
			})
		};

		var fill = new ol.style.Fill({
			color : 'rgba(152,152,152,0.9)'
		});

		var styles = {
			'line' : new ol.style.Style({
				stroke : strokes['line'],
				image : new ol.style.Circle({
					radius : 10,
					stroke : strokes['line']
				})
			}),
			'circle' : new ol.style.Style({
				// stroke: strokes['circle'],
				image : new ol.style.Circle({
					radius : 8,
					stroke : strokes['default']
				})
			}),
			'square' : new ol.style.Style({
				image : new ol.style.RegularShape({
					stroke : strokes['default'],
					points : 4,
					radius : 8,
					angle : Math.PI / 4
				})
			}),
			'triangle' : new ol.style.Style({
				image : new ol.style.RegularShape({
					stroke : strokes['default'],
					points : 3,
					radius : 8
				})
			})
		};

		var extent = this.features_.item(0).getGeometry().getExtent();
		var coorX = (extent[0] + extent[2]) / 2;
		var rotatePositionA = (extent[2] - extent[0]) / 3;
		var rotatePositionB = (extent[3] - extent[1]) / 3;

		var vectorContext = evt.vectorContext;

		mbr = new ol.geom.Polygon([ [ [ extent[0], extent[3] ], [ extent[0], extent[1] ], [ extent[2], extent[1] ], [ extent[2], extent[3] ], [ extent[0], extent[3] ] ] ]);

		if (rotatePositionA > rotatePositionB) {
			line = new ol.geom.LineString([ [ coorX, extent[3] ], [ coorX, extent[3] + rotatePositionB ] ]);
			circle = new ol.geom.Point([ coorX, extent[3] + rotatePositionB ]);
		} else {
			line = new ol.geom.LineString([ [ coorX, extent[3] ], [ coorX, extent[3] + rotatePositionA ] ]);
			circle = new ol.geom.Point([ coorX, extent[3] + rotatePositionA ]);
		}

		vectorContext.drawFeature(new ol.Feature({
			geometry : circle,
			name : 'rotate'
		}), styles['circle']);

		square.push(new ol.geom.Point([ extent[0], extent[1] ]));
		square.push(new ol.geom.Point([ extent[0], extent[3] ]));
		square.push(new ol.geom.Point([ extent[2], extent[1] ]));
		square.push(new ol.geom.Point([ extent[2], extent[3] ]));

		for ( var i in square) {
			vectorContext.drawFeature(new ol.Feature({
				geometry : square[i],
				name : 'scale'
			}), styles['square']);
		}

		triangle.push(new ol.geom.Point([ (extent[0] + extent[2]) / 2, extent[3] ]));
		triangle.push(new ol.geom.Point([ extent[2], (extent[1] + extent[3]) / 2 ]));
		triangle.push(new ol.geom.Point([ (extent[0] + extent[2]) / 2, extent[1] ]));
		triangle.push(new ol.geom.Point([ extent[0], (extent[1] + extent[3]) / 2 ]));

		for ( var i in triangle) {
			styles['triangle'].getImage().setRotation(i * Math.PI / 2);
			vectorContext.drawFeature(new ol.Feature({
				geometry : triangle[i],
				name : 'flip'
			}), styles['triangle']);
		}

		vectorContext.setStyle(styles['line']);
		vectorContext.drawGeometry(mbr);
		vectorContext.drawGeometry(line);
	}
};

/**
 * 선택된 피처들을 담은 객체를 반환한다.
 * 
 * @method gb.interaction.MultiTransform#getFeatures
 * @function
 * @return {ol.Collection.<ol.Feature>}
 */
gb.interaction.MultiTransform.prototype.getFeatures = function() {
	return this.features_;
};

/**
 * style로 그려진 MultiTransform의 버튼들의 영역을 설정하고 cursor가 그 위치에 있을때 해당버튼에 맞는 작업의 이름을
 * String으로 반환한다. 커서가 버튼 영역에 놓여있지 않다면 null 값을 반환한다.
 * 
 * @method gb.interaction.MultiTransform#selectTask_
 * @function
 * @param {ol.Map}
 *            map - MultiTransform 기능이 적용된 맵 객체
 * @param {ol.Feature}
 *            feature - 선택된 Feature 객체
 * @param {Array.
 *            <number>} cursor - 맵 위에서의 마우스 커서 좌표
 * @return {string} 실행해야할 작업명
 */
gb.interaction.MultiTransform.prototype.selectTask_ = function(map, feature, cursor) {

	var AREA = 6;

	var extent = feature.getGeometry().getExtent();
	var scale = [];
	var flip = [];
	var rotate = null;
	var task = null;
	var rotatePositionA = (extent[2] - extent[0]) / 3;
	var rotatePositionB = (extent[3] - extent[1]) / 3;

	if (rotatePositionA > rotatePositionB) {
		rotate = map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] + rotatePositionB ]);
	} else {
		rotate = map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] + rotatePositionA ]);
	}

	scale.push(map.getPixelFromCoordinate([ extent[0], extent[1] ]));
	scale.push(map.getPixelFromCoordinate([ extent[0], extent[3] ]));
	scale.push(map.getPixelFromCoordinate([ extent[2], extent[3] ]));
	scale.push(map.getPixelFromCoordinate([ extent[2], extent[1] ]));

	flip.push(map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[1] ]));
	flip.push(map.getPixelFromCoordinate([ (extent[0] + extent[2]) / 2, extent[3] ]));
	flip.push(map.getPixelFromCoordinate([ extent[0], (extent[1] + extent[3]) / 2 ]));
	flip.push(map.getPixelFromCoordinate([ extent[2], (extent[1] + extent[3]) / 2 ]));

	if ((cursor[0] >= rotate[0] - AREA && cursor[0] <= rotate[0] + AREA) && (cursor[1] >= rotate[1] - AREA && cursor[1] <= rotate[1] + AREA)) {
		task = 'rotate';
	}

	for ( var i in scale) {
		if ((cursor[0] >= scale[i][0] - AREA && cursor[0] <= scale[i][0] + AREA) && (cursor[1] >= scale[i][1] - AREA && cursor[1] <= scale[i][1] + AREA)) {
			(i % 2 === 0) ? task = 'scaleE' : task = 'scaleW';
		}
	}

	for ( var i in flip) {
		if ((cursor[0] >= flip[i][0] - AREA && cursor[0] <= flip[i][0] + AREA) && (cursor[1] >= flip[i][1] - AREA && cursor[1] <= flip[i][1] + AREA)) {
			if (i === '0') {
				task = 'down';
			} else if (i === '1') {
				task = 'up';
			} else if (i === '2') {
				task = 'left';
			} else if (i === '3') {
				task = 'right';
			}
		}
	}

	return task;
};

/**
 * 피처 회전 알고리즘. 'pointerdrag' 이벤트 발생전의 커서 위치값과 이벤트 발생시의 커서값을 인자로 받아 선택된 피쳐의 중점을
 * 기준으로 두 점 사이의 각도를 반환
 * 
 * @method gb.interaction.MultiTransform#rotateAlgorithm_
 * @function
 * @param {Array.
 *            <number>} prevCursorPosition - 'pointerdrag'이벤트 발생 전의 커서 위치값
 * @param {Array.
 *            <number>} currentCursorPosition - 'pointerdrag'이벤트 발생 시의 커서 위치값
 * @return {number} 사이각을 radian 형식으로 반환
 */
gb.interaction.MultiTransform.prototype.rotateAlgorithm_ = function(prevCursorPosition, currentCursorPosition) {
	var feature = this.features_.item(0);
	var result = null;
	var extent = feature.getGeometry().getExtent();
	var pivot = this.flatInteriorPoint_;
	var prev = prevCursorPosition;
	if (!prev) {
		prev = [ (extent[2] + extent[0]) / 2, extent[3] ];
	}
	var current = currentCursorPosition;

	var currentRadi = Math.atan2(current[1] - pivot[1], current[0] - pivot[0]);
	var prevRadi = Math.atan2(prev[1] - pivot[1], prev[0] - pivot[0]);

	if (currentRadi > 0 && prevRadi < 0) {
		result = currentRadi - Math.abs(prevRadi);
	} else if (currentRadi < 0 && prevRadi > 0) {
		result = Math.abs(currentRadi) - prevRadi;
	} else {
		result = currentRadi - prevRadi;
	}
	return result;
};

/**
 * 피처 확대, 축소 알고리즘. 선택된 scale버튼의 이전 좌표값과 pointer를 drag함으로서 변경된 커서의 좌표, 두 좌표값사이
 * 길이의 배율값을 구하여 그 배율의 절대값을 리턴한다.
 * 
 * @method gb.interaction.MultiTransform#scaleAlgorithm_
 * @function
 * @param {ol.Feature}
 *            feature - ol.Feature 객체
 * @param {Array.
 *            <number>} currentCursorPoint - drag를 통해 변경된 커서의 위치
 * @return {Array.<number>} 늘어난 x좌표, y좌표 배율의 절대값
 * @this {gb.interaction.MultiTransform}
 */
gb.interaction.MultiTransform.prototype.scaleAlgorithm_ = function(feature, currentCursorPoint) {
	var map = this.getMap();
	var extent = feature.getGeometry().getExtent();
	var cursor = currentCursorPoint;
	var center = this.flatInteriorPoint_;
	var result = [];

	var cursorPixel = map.getPixelFromCoordinate(currentCursorPoint);
	var centerPixel = map.getPixelFromCoordinate(center);
	var subX = Math.abs(cursorPixel[0] - centerPixel[0]);
	var subY = Math.abs(cursorPixel[1] - centerPixel[1]);
	var magniX = 0;
	var magniY = 0;

	if (subX < 2 || subY < 2) {
		magniX = 1;
		magniY = 1;
	} else {
		magniX = (cursor[0] - center[0]) / (extent[0] - center[0]);
		magniY = (cursor[1] - center[1]) / (extent[1] - center[1]);
	}

	result.push(Math.abs(magniX), Math.abs(magniY));
	return result;
};

/**
 * 피처 플립 알고리즘. 선택한 방향으로 feature를 Flip.
 * 
 * @method gb.interaction.MultiTransform#flipAlgorithm_
 * @function
 * @param {ol.Feature}
 *            feature - ol.Feature 객체
 * @param {String}
 *            direction - Flip을 할 방향("up", "down", "right", "left")
 */
gb.interaction.MultiTransform.prototype.flipAlgorithm_ = function(feature, direction) {
	var extentIndex = null;
	var geometry = feature.getGeometry();
	var extent = feature.getGeometry().getExtent();
	var fcoordi = feature.getGeometry().getFlatCoordinates();
	var coordi = feature.getGeometry().getCoordinates();
	var newMultiCoordis = [];
	var newCoordis = [];
	var newCoordi = [];
	var newGeometry = null;

	if (direction === "up") {
		extentIndex = 3;
	} else if (direction === "down") {
		extentIndex = 1;
	} else if (direction === "right") {
		extentIndex = 2;
	} else if (direction === "left") {
		extentIndex = 0;
	} else {
		console.error('direction error');
		return;
	}

	if (geometry instanceof ol.geom.LineString || geometry instanceof ol.geom.MultiLineString) {
		for (var i = 0; i < fcoordi.length / 2; i++) {
			if (extentIndex === 1 || extentIndex === 3) {
				if (fcoordi[2 * i + 1] !== extent[extentIndex]) {
					newCoordi.push([ fcoordi[2 * i], 2 * extent[extentIndex] - fcoordi[2 * i + 1] ]);
				} else {
					newCoordi.push([ fcoordi[2 * i], fcoordi[2 * i + 1] ]);
				}
			} else {
				if (fcoordi[2 * i] !== extent[extentIndex]) {
					newCoordi.push([ 2 * extent[extentIndex] - fcoordi[2 * i], fcoordi[2 * i + 1] ]);
				} else {
					newCoordi.push([ fcoordi[2 * i], fcoordi[2 * i + 1] ]);
				}
			}
		}
	} else if (geometry instanceof ol.geom.Polygon) {
		for (var i = 0; i < coordi.length; i++) {
			newCoordi = [];
			for (var j = 0; j < coordi[i].length; j++) {
				if (extentIndex === 1 || extentIndex === 3) {
					if (coordi[i][j][1] !== extent[extentIndex]) {
						newCoordi.push([ coordi[i][j][0], 2 * extent[extentIndex] - coordi[i][j][1] ]);
					} else {
						newCoordi.push([ coordi[i][j][0], coordi[i][j][1] ]);
					}
				} else {
					if (coordi[i][j][0] !== extent[extentIndex]) {
						newCoordi.push([ 2 * extent[extentIndex] - coordi[i][j][0], coordi[i][j][1] ]);
					} else {
						newCoordi.push([ coordi[i][j][0], coordi[i][j][1] ]);
					}
				}
			}
			newCoordis.push(newCoordi);
		}
	} else if (geometry instanceof ol.geom.MultiPolygon) {
		for (var i = 0; i < coordi.length; i++) {
			newCoordis = [];
			for (var j = 0; j < coordi[i].length; j++) {
				newCoordi = [];
				for (var k = 0; k < coordi[i][j].length; k++) {
					if (extentIndex === 1 || extentIndex === 3) {
						if (coordi[i][j][k][1] !== extent[extentIndex]) {
							newCoordi.push([ coordi[i][j][k][0], 2 * extent[extentIndex] - coordi[i][j][k][1] ]);
						} else {
							newCoordi.push([ coordi[i][j][k][0], coordi[i][j][k][1] ]);
						}
					} else {
						if (coordi[i][j][k][0] !== extent[extentIndex]) {
							newCoordi.push([ 2 * extent[extentIndex] - coordi[i][j][k][0], coordi[i][j][k][1] ]);
						} else {
							newCoordi.push([ coordi[i][j][k][0], coordi[i][j][k][1] ]);
						}
					}
				}
				newCoordis.push(newCoordi);
			}
			newMultiCoordis.push(newCoordis);
		}
	}

	if (geometry instanceof ol.geom.MultiPolygon) {
		newGeometry = new ol.geom.MultiPolygon(newMultiCoordis);
	} else if (geometry instanceof ol.geom.Polygon) {
		newGeometry = new ol.geom.Polygon(newCoordis);
	} else if (geometry instanceof ol.geom.MultiLineString) {
		newGeometry = new ol.geom.MultiLineString([ newCoordi ]);
	} else if (geometry instanceof ol.geom.LineString) {
		newGeometry = new ol.geom.LineString(newCoordi);
	} else {
		console.error("feature type error");
		return;
	}
	feature.setGeometry(newGeometry);
};

/**
 * MultiTransform event type
 * 
 * @enum {string}
 */
gb.interaction.MultiTransformEventType = {
	/**
	 * Triggered upon feature multiTransform start
	 * 
	 * @event gb.interaction.MultiTransform#transformstart
	 * @type {string}
	 * @api
	 */
	TRANSFORMSTART : 'transformstart',
	/**
	 * Triggered upon feature multiTransforming
	 * 
	 * @event gb.interaction.MultiTransform#transforming
	 * @api
	 */
	TRANSFORMING : 'transforming',
	/**
	 * Triggered upon feature multiTransform end
	 * 
	 * @event gb.interaction.MultiTransform#transformend
	 * @api
	 */
	TRANSFORMEND : 'transformend'
};

// gb.interaction.MultiTransform.Event = function(type, feature,
// mapBrowserPointerEvent) {
//
// ol.events.condition.call(this, type);
//
// /**
// * The features being modified.
// *
// * @type {ol.Feature}
// * @api
// */
// this.feature = feature;
//
// /**
// * Associated {@link ol.MapBrowserEvent}.
// *
// * @type {ol.MapBrowserEvent}
// * @api
// */
// this.mapBrowserEvent = mapBrowserPointerEvent;
// };
// ol.inherits(gb.interaction.MultiTransform.Event, ol.events.condition);

/**
 * @classdesc Events emitted by {@link module:ol/interaction/Draw~Draw}
 *            instances are instances of this type.
 */

if (!gb.interaction.MultiTransform.Event) {
	gb.interaction.MultiTransform["Event"] = {};
}
gb.interaction.MultiTransform.Event.TransformEvent = function(type, angle, ratio) {
	this.propagationStopped;

	/**
	 * The event type.
	 * 
	 * @type {string}
	 * @api
	 */
	this.type = type;

	/**
	 * The event target.
	 * 
	 * @type {Object}
	 * @api
	 */
	this.target = null;

	this.angle_ = angle;
	this.ratio_ = ratio;

}
