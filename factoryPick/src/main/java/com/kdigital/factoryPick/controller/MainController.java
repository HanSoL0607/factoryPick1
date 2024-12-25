package com.kdigital.factoryPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	 @GetMapping("/")
	    public String index(Model model) {
		 return "index";
	}
	 
	 @GetMapping("/login")
	 public String login() {
	     return "login/login";  // 홈 화면으로 리다이렉트
	 }
	 
	 @GetMapping("/agent")
	 public String agent() {
	     return "login/agent";  // 홈 화면으로 리다이렉트
	 }
	 @GetMapping("/intro")
	 public String intro() {
		 return "intro";
	 }
	 
}
