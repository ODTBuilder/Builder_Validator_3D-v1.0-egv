package com.gitrnd.qaproducer.qa.service;

import java.io.File;

import org.json.simple.JSONObject;

public interface QAWebService {
	public void validate(JSONObject param) throws Throwable;
	public File[] sortFileList(File[] files, final int compareType);
}
