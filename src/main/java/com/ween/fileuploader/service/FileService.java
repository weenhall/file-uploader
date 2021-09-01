package com.ween.fileuploader.service;

import com.ween.fileuploader.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {

	Optional<Attachment> findById(String id);

	List<Attachment> findBySource(String source);

	void save(Attachment attachment, MultipartFile file) throws Exception;

	void update(Attachment attachment,MultipartFile file) throws Exception;

	void delete(String id);
}
