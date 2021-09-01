package com.ween.fileuploader.controller;

import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/upload/{source}")
	public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile file
			, @PathVariable(value = "source") String source,@RequestParam Map<String,String> params){
		try{
			Attachment attachment=new Attachment();
			attachment.setFileSource(source);
			attachment.setFileName(params.getOrDefault("name","temp"));
			attachment.setFileType(params.get("type"));
			attachment.setFileBucket("temp");
			fileService.save(attachment,file);
			return ResponseEntity.ok("上传成功");
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败");
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Attachment> attachment(@PathVariable(value = "id") String id){
		return ResponseEntity.ok(fileService.findById(id).orElse(new Attachment()));
	}

	@GetMapping("/list/{source}")
	public ResponseEntity<List<Attachment>> attachments(@PathVariable(value = "source") String source){
		return ResponseEntity.ok(fileService.findBySource(source));
	}

	@PostMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(value = "id") String id){
		fileService.delete(id);
		return ResponseEntity.ok("删除成功");
	}
}
