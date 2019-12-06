package com.gitrnd.qaproducer.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gitrnd.qaproducer.file.service.DeleteFileService;
import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.service.FileStatusService;
import com.gitrnd.qaproducer.user.domain.User;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("deleteFileService")
public class DeleteFileServiceImpl extends EgovAbstractServiceImpl implements DeleteFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFileServiceImpl.class);

	private static final String serverhost;
	private static final String baseDir;
	private static final String port;
	private static final String contextPath;
	private static final String baseDrive;
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
		baseDir = properties.getProperty("baseDir");
		baseDrive = properties.getProperty("basedrive");
		serverhost = properties.getProperty("serverhost");
		port = properties.getProperty("port");
		contextPath = properties.getProperty("contextPath");
		apacheHost = properties.getProperty("apacheHost");
		apachePort = properties.getProperty("apachePort");
	}

	@Resource(name = "fileStatusService")
	private FileStatusService fileStatusService;

	public boolean deleteErrorZipFile(User loginUser, String[] filenames) throws Exception {
		String uploadPath = baseDrive + ":" + File.separator + baseDir + File.separator + loginUser.getUid()
				+ File.separator + "error";

		File file = new File(uploadPath);
		if (file.exists()) { // 파일존재여부확인
			if (file.isDirectory()) { // 파일이 디렉토리인지 확인
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					File[] zipFiles = files[i].listFiles();
					for (int j = 0; j < zipFiles.length; j++) {
						if (Arrays.asList(filenames).contains(zipFiles[j].getName())) {
							if (zipFiles[j].delete()) {
								LOGGER.info("{} file(error layer) delete success!", zipFiles[j].getName());
							} else {
								LOGGER.info("{} file(error layer) delete fail!", zipFiles[j].getName());
							}
						}
					}
					if (files[i].delete()) {
						LOGGER.info("{} directory delete success!", files[i].getName());
					} else {
						LOGGER.info("{} directory delete fail!", files[i].getName());
					}
				}
			}

		} else {
			LOGGER.info("ERROR! : {} directory is not exist!", uploadPath);
			return false;
		}

		return true;
	}

	public boolean deleteOriginalZipFileWithPath(String user, String path) throws Exception {

		File file = new File(path);

		if (file.exists()) { // 파일존재여부확인

			if (file.isDirectory()) { // 파일이 디렉토리인지 확인

				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {

					if (files[i].getName().equals(file.getName())) {
						if (files[i].delete()) {
							LOGGER.info("{} file delete success!", files[i].getName());
						} else {
							LOGGER.info("{} file delete fail!", files[i].getName());
							return false;
						}
					}
				}

				if (file.delete()) {
					LOGGER.info("{} directory delete success!", file.getName());

				} else {
					LOGGER.info("{} directory delete fail!", file.getName());
					return false;
				}
			} else {
				LOGGER.info("ERROR! : {} is not directory", path);
				return false;
			}
		} else {
			LOGGER.info("ERROR! : {} directory is not exist!", path);
			return false;
		}

		return true;
	}

	public boolean deleteOriginalZipFile(String user, int fid) throws Exception {

		FileStatus fileStatus = fileStatusService.retrieveFileStatusById(fid);
		Pattern p = Pattern.compile("(?<=time\\=).*(?=\\&)");
		Matcher m = p.matcher(fileStatus.getLocation());

		while (m.find()) {
			String path = baseDrive + ":" + File.separator + baseDir + File.separator + user + File.separator + "upload"
					+ File.separator + m.group();
			File file = new File(path);
			if (file.exists()) { // 파일존재여부확인
				if (file.isDirectory()) { // 파일이 디렉토리인지 확인
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].getName().equals(fileStatus.getFname())) {
							if (files[i].delete()) {
								LOGGER.info("{} file delete success!", files[i].getName());
							} else {
								LOGGER.info("{} file delete fail!", files[i].getName());
								return false;
							}
						}
					}
					if (file.delete()) {
						LOGGER.info("{} directory delete success!", file.getName());
					} else {
						LOGGER.info("{} directory delete fail!", file.getName());
						return false;
					}
				} else {
					LOGGER.info("ERROR! : {} is not directory", path);
					return false;
				}
			} else {
				LOGGER.info("ERROR! : {} directory is not exist!", path);
				return false;
			}
		}
		return true;
	}

	public boolean deleteOriginalUnZipFile(String user, int fid) throws Exception {

		FileStatus fileStatus = fileStatusService.retrieveFileStatusById(fid);
		Pattern p = Pattern.compile("(?<=time\\=).*(?=\\&)");
		Matcher m = p.matcher(fileStatus.getLocation());

		while (m.find()) {
			String path = baseDrive + ":" + File.separator + baseDir + File.separator + user + File.separator + "upload"
					+ File.separator + m.group();
			File file = new File(path);
			if (file.exists()) { // 파일존재여부확인
				if (file.isDirectory()) { // 파일이 디렉토리인지 확인
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].getName().equals(fileStatus.getFname())) {
							if (files[i].delete()) {
								LOGGER.info("{} file delete success!", files[i].getName());
							} else {
								LOGGER.info("{} file delete fail!", files[i].getName());
								return false;
							}
						}
					}
					if (file.delete()) {
						LOGGER.info("{} directory delete success!", file.getName());
					} else {
						LOGGER.info("{} directory delete fail!", file.getName());
						return false;
					}
				} else {
					LOGGER.info("ERROR! : {} is not directory", path);
					return false;
				}
			} else {
				LOGGER.info("ERROR! : {} directory is not exist!", path);
				return false;
			}
		}
		return true;
	}
}
