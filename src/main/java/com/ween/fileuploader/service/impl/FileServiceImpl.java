package com.ween.fileuploader.service.impl;

import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.repository.FileRepository;
import com.ween.fileuploader.service.FileService;
import com.ween.fileuploader.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public Optional<Attachment> findById(String id) {
		return fileRepository.findById(id);
	}

	@Override
	public List<Attachment> findBySource(String source) {
		return fileRepository.findByFileSource(source);
	}

	@Override
	public void save(Attachment attachment, MultipartFile file) throws Exception{
		fileStorageService.save(attachment,file);
		fileRepository.save(attachment);
	}

	@Override
	public void update(Attachment attachment, MultipartFile file) throws Exception {
		fileStorageService.update(attachment,file);
		fileRepository.save(attachment);
	}

	@Override
	public void delete(String id) {
		fileRepository.deleteById(id);
	}
}
