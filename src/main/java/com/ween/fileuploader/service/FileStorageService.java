package com.ween.fileuploader.service;

import com.ween.fileuploader.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String save(Attachment attachment, MultipartFile file) throws Exception;

	void update(Attachment attachment,MultipartFile file) throws Exception;
}
