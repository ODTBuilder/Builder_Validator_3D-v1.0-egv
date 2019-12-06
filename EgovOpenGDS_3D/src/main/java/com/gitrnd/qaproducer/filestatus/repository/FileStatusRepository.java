package com.gitrnd.qaproducer.filestatus.repository;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.mapper.FileStatusMapper;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("fileStatusRepository")
public class FileStatusRepository extends EgovAbstractMapper{

	@Resource(name = "fileStatusMapper")
	private FileStatusMapper fileStatusMapper;

	public FileStatus retrieveFileStatusById(int fid) {
		return fileStatusMapper.retrieveFileStatusById(fid);
	}

	public void createFileStatus(FileStatus fileStatus) {
		fileStatusMapper.createFileStatus(fileStatus);
	}

	public void updateFileStatus(FileStatus fileStatus) {
		fileStatusMapper.updateFileStatus(fileStatus);
	}

	public void deleteFileStatus(FileStatus fs) throws RuntimeException {
		fileStatusMapper.deleteFileStatus(fs);
	}
}
