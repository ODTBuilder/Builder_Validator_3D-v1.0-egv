package com.gitrnd.qaproducer.file.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gitrnd.qaproducer.file.service.UploadService;
import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.service.FileStatusService;
import com.gitrnd.qaproducer.user.domain.User;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("uploadService")
public class UploadServiceImpl extends EgovAbstractServiceImpl implements UploadService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

	private static final String serverhost;
	private static final String baseDir;
	private static final String port;
	private static final String contextPath;
	private static final String baseDrive;

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
		serverhost = properties.getProperty("serverhost");
		port = properties.getProperty("port");
		contextPath = properties.getProperty("contextPath");
		baseDrive = properties.getProperty("basedrive");
	}

	@Resource(name = "fileStatusService")
	private FileStatusService fileStatusService;

	public List<FileStatus> SaveFile(MultipartHttpServletRequest request, User loginUser) throws Exception {
		String basePath = baseDrive + ":" + File.separator + baseDir;
		String uploadPath = basePath + File.separator + loginUser.getUid() + File.separator + "upload";

		long date = System.currentTimeMillis();
		String cTimeStr = new SimpleDateFormat("yyMMdd" + "_" + "HHmmss").format(date);

		String uniquePath = uploadPath + File.separator + cTimeStr;
		String webPath = "http://" + serverhost + ":" + port + contextPath + "/downloadzip.do" + "?" + "user="
				+ loginUser.getUid() + "&time=" + cTimeStr;

		// 최상위 디렉토리 생성
		File base = new File(basePath);
		if (!base.exists()) {
			base.mkdirs();
		}

		// 업로드 디렉토리 생성
		File upload = new File(uploadPath);
		if (!upload.exists()) {
			upload.mkdirs();
		}

		// 요청 고유 디렉토리 생성
		File unique = new File(uniquePath);
		if (!unique.exists()) {
			unique.mkdirs();
		}

		LinkedList<FileStatus> files = new LinkedList<FileStatus>();

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			LOGGER.info("{} uploaded!", mpf.getOriginalFilename());

			try {
				// 2.3 create new fileMeta
				FileStatus fileStatus = new FileStatus();
				String trimFileName = mpf.getOriginalFilename().replaceAll(" ", "");
				String encodeFileName = URLEncoder.encode(trimFileName, "UTF-8");

				webPath = webPath + "&file=" + encodeFileName;
				fileStatus.setLocation(webPath);
				fileStatus.setFname(mpf.getOriginalFilename());
				fileStatus.setCtime(new Timestamp(date));
				fileStatus.setStatus(1);
				fileStatus.setUidx(loginUser.getIdx());
				// fileStatus.setBytes(mpf.getBytes());

				String saveFilePath = uniquePath + File.separator + trimFileName;
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(saveFilePath));

				// copy file to local disk (make sure the path "e.g.
				// D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), stream);

				fileStatusService.createFileStatus(fileStatus);
				files.add(fileStatus);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return files;
	}

	public void SaveErrorFile(MultipartHttpServletRequest request) throws Exception {
		String basePath = baseDrive + ":" + File.separator + baseDir;
		String uploadPath = basePath + File.separator + request.getParameter("user") + File.separator + "error";
		String uniquePath = uploadPath + File.separator + request.getParameter("time");

		// 최상위 디렉토리 생성
		File base = new File(basePath);
		if (!base.exists()) {
			base.mkdirs();
		}

		// 업로드 디렉토리 생성
		File upload = new File(uploadPath);
		if (!upload.exists()) {
			upload.mkdirs();
		}

		// 요청 고유 디렉토리 생성
		File unique = new File(uniquePath);
		if (!unique.exists()) {
			unique.mkdirs();
		}

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			LOGGER.info("{} uploaded!", mpf.getOriginalFilename());
			try {
				String saveFilePath = uniquePath + File.separator + mpf.getOriginalFilename();
				LOGGER.info("저장 파일 경로:{}", saveFilePath);
				// copy file to local disk (make sure the path "e.g.
				// D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(saveFilePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
