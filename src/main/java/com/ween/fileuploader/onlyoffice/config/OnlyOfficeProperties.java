package com.ween.fileuploader.onlyoffice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "onlyoffice")
public class OnlyOfficeProperties {

	private String bucketName;
	private String serverUrl;
	private String webApi;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getWebApi() {
		return webApi;
	}

	public void setWebApi(String webApi) {
		this.webApi = webApi;
	}
}
