package com.ween.fileuploader.config;

import com.ween.fileuploader.service.FileStorageService;
import com.ween.fileuploader.service.impl.MinioFileStorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


	/**
	 * 使用Minio存储
	 * @return FileStorageService
	 */
	@Bean
	public FileStorageService fileStorageService(){
		return new MinioFileStorageServiceImpl();
	}
}
