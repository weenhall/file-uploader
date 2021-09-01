package com.ween.fileuploader.service;

import com.ween.fileuploader.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface OnlyOfficeService {

	Attachment upload(MultipartFile file, String source, String uploader,String bucketName) throws Exception;

}
