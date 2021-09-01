package com.ween.fileuploader.service.impl;

import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

public class AliOSSFileStorageServiceImpl implements FileStorageService {
	@Override
	public String save(Attachment attachment, MultipartFile file) {
		return null;
	}

	@Override
	public void update(Attachment attachment, MultipartFile file) {

	}
}
