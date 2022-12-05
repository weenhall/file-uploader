package com.ween.fileuploader.controller;

import com.ween.fileuploader.oss.minio.util.MinioUtil;
import io.minio.StatObjectResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/minio")
public class MinioController {

	Logger log= LoggerFactory.getLogger(MinioController.class);

	@Autowired
	private MinioUtil minioUtil;

	@PostMapping("/upload")
	public Object upload(@RequestParam(name = "file") MultipartFile file,
											   @RequestParam(required = false, defaultValue = "salt") String bucketName) {
		Object response = null;
		try {
			response = minioUtil.uploadFile(file, bucketName,"");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传失败 : [{}]", Arrays.asList(e.getStackTrace()));
		}
		return response;
	}

	@DeleteMapping("/delete/{objectName}")
	public void delete(@PathVariable("objectName") String objectName,
					   @RequestParam(required = false, defaultValue = "salt") String bucketName) throws Exception {
		minioUtil.removeObject(bucketName, objectName);
		log.error("删除成功");
	}

	@RequestMapping("/download/{objectName}")
	private void download(@PathVariable("objectName") String objectName,
						  @RequestParam(required = false, defaultValue = "salt") String bucketName, String fileName, HttpServletResponse response) {
		InputStream in = null;
		try {
			StatObjectResponse stat = minioUtil.getObjectInfo(bucketName, objectName);
			response.setContentType(stat.contentType());
			if(StringUtils.isNoneEmpty(fileName)){
				response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
						" attachment;filename=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
			}
			in = minioUtil.getObject(bucketName, objectName);
			IOUtils.copyLarge(in, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
