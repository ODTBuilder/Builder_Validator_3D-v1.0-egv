package com.gitrnd.qaproducer.file.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gitrnd.qaproducer.file.service.RequestService;
import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.service.FileStatusService;
import com.gitrnd.qaproducer.qa.domain.QACategory;
import com.gitrnd.qaproducer.qa.domain.QAProgress;
import com.gitrnd.qaproducer.qa.service.QACategoryService;
import com.gitrnd.qaproducer.qa.service.QAFileService;
import com.gitrnd.qaproducer.qa.service.QAProgressService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("requestService")
public class RequestServiceImpl extends EgovAbstractServiceImpl implements RequestService{

	// qa progress
	protected static int fileUpload = 1;

	@Resource(name = "fileStatusService")
	private FileStatusService fileStatusService;

	@Resource(name = "qaProgressService")
	private QAProgressService qapgService;

	@Resource(name = "qaCategoryService")
	private QACategoryService qaCatService;

	@Resource(name = "qaFileService")
	private QAFileService qaService;

	@Async("threadPoolTaskExecutor")
	public void requestFileQAList(List<FileStatus> files, int cid, String fileformat, String crs, String qaVer,
			String qaType, String bPrid, int prid) {

		for (int k = 0; k < files.size(); k++) {

			FileStatus file = files.get(k);
			int fIdx = file.getFid();
			String fileName = file.getFname();
			int uIdx = file.getUidx();
			Long catetoryIdx = (long) cid;
			int cIdx = catetoryIdx.intValue();
			QACategory qaCat = qaCatService.retrieveQACategoryByIdx(cIdx);
			String qaTitle = qaCat.getTitle();

			// insert file upload progress
			QAProgress progress = new QAProgress();
			progress.setQaState(fileUpload);
			progress.setOriginName(fileName);
			progress.setfIdx(fIdx);
			progress.setuIdx(uIdx);
			progress.setQaType(qaTitle);
			progress.setFileType(fileformat);
			progress.setPrid(prid);
			qapgService.insertQARequest(progress);

			int pid = progress.getpIdx();
			JSONObject json = new JSONObject();
			json.put("file", file);
			json.put("category", cid);
			json.put("qaVer", qaVer);
			json.put("qaType", qaType);
			json.put("pid", pid);
			json.put("prid", bPrid);
			json.put("fileformat", fileformat);
			json.put("crs", "EPSG:" + crs);
			json.put("type", "file");

			try {
				qaService.validate(json);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
