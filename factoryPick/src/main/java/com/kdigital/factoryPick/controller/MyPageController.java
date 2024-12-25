package com.kdigital.factoryPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {	
	@GetMapping("/FinalModal")
	public String modal(Model model) {
		model.addAttribute("industrialComplexName", "산업단지명");

		return "mypage/FinalModal";
	}
	
	@GetMapping("/mypage_recommend")
	public String modal_rec(Model model) {
		model.addAttribute("industrialComplexName", "산업단지명");

		return "mypage/mypage_recommend";
	}
}
