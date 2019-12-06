package com.gitrnd.qaproducer.file.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gitrnd.qaproducer.file.service.DownloadService;
import com.gitrnd.qaproducer.user.domain.User;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("downloadService")
public class DownloadServiceImpl extends EgovAbstractServiceImpl implements DownloadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);

	private static final String apacheHost;
	private static final String apachePort;

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classLoader.getResourceAsStream("application.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		apacheHost = properties.getProperty("apacheHost");
		apachePort = properties.getProperty("apachePort");
	}

	public void downloadZip(HttpServletResponse response, String time, String file, String user)
			throws UnsupportedEncodingException {
		String encodeName = URLEncoder.encode(file, "UTF-8");
		String path = "http://" + apacheHost + ":" + apachePort + "/" + user + "/upload/" + time + "/" + encodeName;
		try {
			URL url = new URL(path);
			LOGGER.info("{} has been requested [origin layer]", file);
			InputStream in = url.openStream();
			response.setContentType("application/octet-stream");
			String txt = file;
			char[] txtChar = txt.toCharArray();
			for (int j = 0; j < txtChar.length; j++) {
				if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
					String targetText = String.valueOf(txtChar[j]);
					try {
						txt = txt.replace(targetText, URLEncoder.encode(targetText, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			response.setHeader("Content-disposition", "attachment; filename=" + txt);
			IOUtils.copy(in, response.getOutputStream());
			response.flushBuffer();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void downloadError(HttpServletResponse response, String time, String file, User loginUser)
			throws UnsupportedEncodingException {
		String encodeName = URLEncoder.encode(file, "UTF-8");
		String path = "http://" + apacheHost + ":" + apachePort + "/" + loginUser.getUid() + "/error/" + time + "/"
				+ encodeName;
		try {
			URL url = new URL(path);
			LOGGER.info("{} has been requested [error layer]", file);
			InputStream in = url.openStream();
			response.setContentType("application/octet-stream");
			String txt = file;
			char[] txtChar = txt.toCharArray();
			for (int j = 0; j < txtChar.length; j++) {
				if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
					String targetText = String.valueOf(txtChar[j]);
					try {
						txt = txt.replace(targetText, URLEncoder.encode(targetText, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			response.setHeader("Content-disposition", "attachment; filename=" + txt);
			IOUtils.copy(in, response.getOutputStream());
			response.flushBuffer();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
