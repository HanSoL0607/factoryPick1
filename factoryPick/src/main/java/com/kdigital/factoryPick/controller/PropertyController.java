package com.kdigital.factoryPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyController {

    @GetMapping("/property/intro")
   public String showPropertyPage() {
    	return "property/property";
  }

    
}
