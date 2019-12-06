package com.gitrnd.qaproducer.filestatus.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.repository.FileStatusRepository;
import com.gitrnd.qaproducer.filestatus.service.FileStatusService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("fileStatusService")
@Transactional
public class FileStatusServiceImpl extends EgovAbstractServiceImpl implements FileStatusService{

	@Resource(name = "fileStatusRepository")
	private FileStatusRepository fileStatusRepository;

	@Transactional(readOnly = true)
	public FileStatus retrieveFileStatusById(int fid) {
		return fileStatusRepository.retrieveFileStatusById(fid);
	}

	@Transactional
	public void createFileStatus(FileStatus fileStatus) {
		fileStatusRepository.createFileStatus(fileStatus);
	}

	@Transactional
	public void updateFileStatus(FileStatus fileStatus) {
		fileStatusRepository.updateFileStatus(fileStatus);
	}

	@Transactional
	public boolean deleteFileStatus(FileStatus fs) {
		boolean flag = false;
		try {
			fileStatusRepository.deleteFileStatus(fs);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return flag;
	}
}
