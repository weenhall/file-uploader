package com.ween.fileuploader.controller;

import com.google.common.collect.Maps;
import com.ween.fileuploader.entity.Attachment;
import com.ween.fileuploader.onlyoffice.config.OnlyOfficeProperties;
import com.ween.fileuploader.oss.minio.util.MinioUtil;
import com.ween.fileuploader.service.FileService;
import com.ween.fileuploader.service.OnlyOfficeService;
import com.ween.fileuploader.util.IpAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/onlyoffice")
public class OnlyOfficeController {

	private static final String[] DOCUMENT_TYPE= {".doc",".docx",".wps"};
	private static final int DOCUMENT_STATUS_TWO=2;
	private static final int DOCUMENT_STATUS_SIX=6;

	@Autowired
	private OnlyOfficeProperties properties;
	@Autowired
	private OnlyOfficeService onlyOfficeService;
	@Autowired
	private FileService fileService;
	@Autowired
	private MinioUtil minioUtil;

	@GetMapping("/editor/{editable}/{id}")
	public String documentEditor(@PathVariable("id") String id, @PathVariable("editable") String editable, Model model) throws Exception {
		Attachment attachment=fileService.findById(id).orElse(new Attachment());
		attachment.setEditable("edit".equals(editable));
		attachment.setId(attachment.getId().concat("_").concat(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")))));
		String serverIp= IpAddressUtil.getServerIp();
		String [] urlPath=attachment.getFileUrl().split("/");
		String fileUrl=minioUtil.getObjectURL(urlPath[1],urlPath[2],1);
		Map<String,Object> map=new HashMap<>(5);
		map.put("officeWebApi",properties.getWebApi());
		map.put("fileUrl",fileUrl);
		map.put("callbackUrl",serverIp.concat(":").concat(properties.getServerUrl()));
		map.put("userId","admin");
		map.put("userName","管理员");
		model.addAttribute("data",map);
		model.addAttribute("attachment",attachment);
		return "officeEditor";
	}

	@PostMapping("/upload/{source}")
	@ResponseBody
	public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file
			, @PathVariable("source") String source, String uploader) {
		String originalName=file.getOriginalFilename();
		String suffix=originalName.substring(originalName.lastIndexOf("."));
		if(Arrays.stream(DOCUMENT_TYPE).noneMatch(s-> Objects.equals(s,suffix))){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("不支持的文件类型");
		}
		Attachment attachment = null;
		try{
			attachment=onlyOfficeService.upload(file,source,uploader,properties.getBucketName());
		}catch (Exception e){
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(attachment);
	}

	@PostMapping("/callback")
	@ResponseBody
	public Map<String,Object> callback(@RequestBody Map<String,Object> request){
		int status= (int) request.get("status");
		if(status==DOCUMENT_STATUS_TWO||status==DOCUMENT_STATUS_SIX){
			String downloadUri = (String) request.get("url");
			URL url = null;
			try {
				url = new URL(downloadUri);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream inputStream = connection.getInputStream();
				String documentKey=String.valueOf(request.get("key"));
				Attachment attachment=fileService.findById(StringUtils.split(documentKey,"_")[0]).orElse(new Attachment());
				String [] urlPath=attachment.getFileUrl().split("/");
				//更新文档
				minioUtil.putObject(urlPath[1],urlPath[2],inputStream, inputStream.available(), attachment.getFileType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (Map<String, Object>) Maps.newHashMap().put("error","0");
	}
}
