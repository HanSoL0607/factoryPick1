package com.kdigital.factoryPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdigital.factoryPick.service.RecommendationSurveyService;


@Controller
public class MainResultController {

    private final RecommendationSurveyService recommendationSurveyService;

    public MainResultController(RecommendationSurveyService recommendationSurveyService) {
        this.recommendationSurveyService = recommendationSurveyService;
    }
    
    @GetMapping("/mainresult")
    public String mainResultPage() {
        return "recommendation_result/mainresult";
    }

}

