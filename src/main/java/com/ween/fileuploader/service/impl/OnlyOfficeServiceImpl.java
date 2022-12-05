package com.ween.fileuploader.service.impl;

import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.service.OnlyOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OnlyOfficeServiceImpl implements OnlyOfficeService {

	@Autowired
	private FileServiceImpl fileService;

	@Override
	public Attachment upload(MultipartFile file, String source, String uploader, String bucketName) throws Exception {
		List<Attachment> attachments=fileService.findBySource(source);
		Attachment attachment=null;
		if(attachments.size()>0){
			attachment=attachments.get(0);
		}
		if(attachment==null){
			attachment=new Attachment();
		}

		attachment.setFileName(file.getOriginalFilename());
		attachment.setFileType(file.getContentType());
		attachment.setFileSource(source);
		attachment.setFileBucket(bucketName);
		if(attachments.size()>0){
			fileService.update(attachment,file);
		}else{
			fileService.save(attachment,file);
		}
		return attachment;
	}
}
