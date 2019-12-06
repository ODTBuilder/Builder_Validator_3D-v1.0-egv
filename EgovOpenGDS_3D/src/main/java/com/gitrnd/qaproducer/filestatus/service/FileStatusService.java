package com.gitrnd.qaproducer.filestatus.service;

import com.gitrnd.qaproducer.filestatus.domain.FileStatus;

public interface FileStatusService {
	public FileStatus retrieveFileStatusById(int fid);

	public void createFileStatus(FileStatus fileStatus);

	public void updateFileStatus(FileStatus fileStatus);

	public boolean deleteFileStatus(FileStatus fs);
}
