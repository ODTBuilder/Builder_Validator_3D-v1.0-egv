package com.gitrnd.qaproducer.file.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
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

	@Override
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

	@Override
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
	
	@Override
	public JSONObject save3dtilesFile(MultipartHttpServletRequest request) throws Exception {

		boolean succ = true;
		
		JSONObject obj = new JSONObject();
		

		String user = request.getParameter("user");
		String time = request.getParameter("time");

		String basePath = baseDrive + ":" + File.separator + baseDir;
		String uploadPath = basePath + File.separator + user + File.separator + "upload" + File.separator + time
				+ File.separator + "3dtiles";

		File path = new File(uploadPath);
		if (!path.exists()) {
			path.mkdirs();
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
				String zipFile = path + File.separator + mpf.getOriginalFilename();
				LOGGER.info("저장 파일 경로:{}", zipFile);
				// copy file to local disk (make sure the path "e.g.
				// D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(zipFile));
				try {
					decompress(zipFile, uploadPath);
					File file = new File(zipFile);
					file.delete();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					succ = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				succ = false;
			}
		}
		
		obj.put("succ", succ);
		obj.put("path", uploadPath);
		
		return obj;
	}
	private void decompress(String zipFileName, String directory) throws Throwable {

		File zipFile = new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;
		try {
			// 파일 스트림
			fis = new FileInputStream(zipFile);
			// Zip 파일 스트림
			zis = new ZipInputStream(fis);
			// entry가 없을때까지 뽑기
			while ((zipentry = zis.getNextEntry()) != null) {
				String filename = zipentry.getName();
				File file = new File(directory, filename);
				// entiry가 폴더면 폴더 생성
				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					// 파일이면 파일 만들기
					createFile(file, zis);
				}
			}
		} catch (Throwable e) {
			throw e;
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
	}

	/**
	 * 파일 만들기 메소드
	 * 
	 * @param file 파일
	 * @param zis  Zip스트림
	 */
	private void createFile(File file, ZipInputStream zis) throws Throwable {
		// 디렉토리 확인
		File parentDir = new File(file.getParent());
		// 디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		// 파일 스트림 선언
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			// Zip스트림으로부터 byte뽑아내기
			while ((size = zis.read(buffer)) > 0) {
				// byte로 파일 만들기
				fos.write(buffer, 0, size);
			}
		} catch (Throwable e) {
			throw e;
		}
	}

}
