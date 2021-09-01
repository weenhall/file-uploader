package com.ween.fileuploader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/uppy")
public class UppyController {

	@RequestMapping(value = "/")
	public String uppy(Model model){
		return "uppy";
	}
}
