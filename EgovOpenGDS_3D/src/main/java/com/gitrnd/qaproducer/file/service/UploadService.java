package com.gitrnd.qaproducer.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.user.domain.User;

public interface UploadService {
	public List<FileStatus> SaveFile(MultipartHttpServletRequest request, User loginUser) throws Exception;
	public void SaveErrorFile(MultipartHttpServletRequest request) throws Exception;
	
}
