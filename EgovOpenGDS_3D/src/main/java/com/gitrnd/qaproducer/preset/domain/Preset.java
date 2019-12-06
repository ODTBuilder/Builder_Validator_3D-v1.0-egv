package com.gitrnd.qaproducer.preset.domain;

public class Preset {

	private int pid;
	private String name;
	private int cat;
	private String title;
	private String layerDef;
	private String optionDef;
	private int uidx;
	private boolean bflag;
	private String support;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCat() {
		return cat;
	}

	public void setCat(int cat) {
		this.cat = cat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLayerDef() {
		return layerDef;
	}

	public void setLayerDef(String layerDef) {
		this.layerDef = layerDef;
	}

	public String getOptionDef() {
		return optionDef;
	}

	public void setOptionDef(String optionDef) {
		this.optionDef = optionDef;
	}

	public int getUidx() {
		return uidx;
	}

	public void setUidx(int uidx) {
		this.uidx = uidx;
	}

	public boolean isBflag() {
		return bflag;
	}

	public void setBflag(boolean bflag) {
		this.bflag = bflag;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

}
