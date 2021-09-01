package com.ween.fileuploader.service.impl;

import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.oss.minio.util.MinioUtil;
import com.ween.fileuploader.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MinioFileStorageServiceImpl implements FileStorageService {

	@Autowired
	private MinioUtil minioUtil;

	@Override
	public String save(Attachment attachment, MultipartFile file) throws Exception{
		Object response=minioUtil.uploadFile(file,attachment.getFileBucket(),attachment.getFileType());
		return response.toString();
	}

	@Override
	public void update(Attachment attachment, MultipartFile file) throws Exception{
		String [] urlPath=attachment.getFileUrl().split("/");
		minioUtil.putObject(urlPath[1],urlPath[2],file.getInputStream(), file.getInputStream().available(), attachment.getFileType());
	}
}
