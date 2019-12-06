package com.gitrnd.qaproducer.file.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import com.gitrnd.qaproducer.user.domain.User;

public interface DownloadService {
	public void downloadZip(HttpServletResponse response, String time, String file, String user)
			throws UnsupportedEncodingException;
	
	public void downloadError(HttpServletResponse response, String time, String file, User loginUser)
			throws UnsupportedEncodingException;
}
