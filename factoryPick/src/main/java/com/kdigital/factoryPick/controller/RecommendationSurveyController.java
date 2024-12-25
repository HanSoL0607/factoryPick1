package com.kdigital.factoryPick.controller;

import com.kdigital.factoryPick.dto.RecommendationSurveyDTO;
import com.kdigital.factoryPick.dto.SurveyResultDTO;
import com.kdigital.factoryPick.dto.TransportationPreferenceDTO;
import com.kdigital.factoryPick.entity.SurveyResultEntity;
import com.kdigital.factoryPick.entity.UserEntity;
import com.kdigital.factoryPick.repository.SurveyResultRepository;
import com.kdigital.factoryPick.service.RecommendationSurveyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;	// 사용자 입력값 세션 저장
import com.fasterxml.jackson.databind.ObjectMapper;  // JSON 파싱을 위한 라이브러리


@Controller
@RequestMapping("/recommendation")
public class RecommendationSurveyController {

    @Autowired
    private RecommendationSurveyService recommendationSurveyService;
    @Autowired
    private SurveyResultRepository surveyResultRepository;
    @Autowired
    private HttpSession session;
    
    // 1. 추천 시스템 첫 화면
    @GetMapping("/recommendation_intro")
    public String recommendationIntro() {
        return "recommendation/recommendation_intro";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~ GET 모음(페이지 이동) ~~~~~~~~~~~~~~~~~~~~~~
    // 지역 선택
    @GetMapping("/region_choice") 
    public String showRegionChoicePage() {
        return "recommendation/region_choice";
    }
    // 교통 선택
    @GetMapping("/transportation_choice")
    public String showTransportationChoicePage() {
        return "recommendation/transportation_choice";
    }
    // 토지 지가 선택
    @GetMapping("/payable_choice_copy")
    public String showPayableChoicePage() {
        return "recommendation/payable_choice_copy";
    }
    // 업종 선택
    @GetMapping("/industry_choice")
    public String showIndustryChoicePage() {
        return "recommendation/industry_choice";
    }
    // 생산품,원자재(마지막 입력값) 선택
    @GetMapping("/last_choice")
    public String showLastChoicePage() {
        return "recommendation/last_choice";
    }
    
    // 1. 지역 선택 저장
    @PostMapping("/region_choice")
    public String saveRegionChoice(@RequestParam("region") String region, HttpSession session) {
        session.setAttribute("region", region);  // 지역 값을 "세션"에 저장
        System.out.println("저장된 지역: " + region);  // 확인용 로그
        return "redirect:/recommendation/transportation_choice";
    }

    // 2. 교통수단 선택 저장
    @PostMapping("/transportation_choice")
    public String saveTransportationChoice(
            @RequestParam("highwayPreference") int highwayPreference,
            @RequestParam("railwayPreference") int railwayPreference,
            @RequestParam("portPreference") int portPreference,
            @RequestParam("airportPreference") int airportPreference,
            HttpSession session) {

        // 교통 우선순위 DTO 생성
        TransportationPreferenceDTO transportation = new TransportationPreferenceDTO(
            highwayPreference, railwayPreference, portPreference, airportPreference
        );

        // 세션에 저장
        session.setAttribute("transportation", transportation);
        System.out.println("저장된 교통: " + transportation);  // 확인용 로그
        return "redirect:/recommendation/payable_choice_copy"; 
    }

    // 3. 최대 토지 지가 저장
    @PostMapping("/payable_choice_copy")
    public String savePayableChoice(@RequestParam("landPrice") Integer landPrice, HttpSession session) {
        if (landPrice == null) {
            throw new IllegalStateException("토지 지가 선택이 되지 않았습니다.");
        }
        System.out.println("저장할 토지 가격: " + landPrice);
        session.setAttribute("landPrice", landPrice);  // 세션에 저장
        System.out.println("세션에 저장된 토지 가격: " + session.getAttribute("landPrice")); // 확인용 로그
        return "redirect:/recommendation/industry_choice";
    }


    // 4. 업종 선택 저장
    @PostMapping("/industry_choice")
    public String saveIndustryChoice(@RequestParam("industry") String industry, HttpSession session) {
        session.setAttribute("industry", industry);  // 값을 세션에 저장
        System.out.println("저장된 업종: " + industry); // 확인용 로그
        return "redirect:/recommendation/last_choice";
    }

    // 5. 생산품, 원자재 입력 완료 후 모든 데이터 전송
    @PostMapping("/submit")
    public String submitSurvey(@RequestParam("product") String product,
                               @RequestParam("rawMaterial") String rawMaterial,
                               @AuthenticationPrincipal OAuth2User oAuth2User,
                               HttpSession session, Model model) throws Exception {
    	
    	session.setAttribute("product", product);
    	System.out.println("저장된 생산품: " + product); // 확인용 로그
    	session.setAttribute("rawMaterial", rawMaterial);
    	System.out.println("저장된 원자재: " + rawMaterial); // 확인용 로그
    	
    	
        // 세션에 저장된 모든 입력값 체크용
        String region = (String) session.getAttribute("region");
        if (region == null) {
            throw new IllegalStateException("지역 선택이 되지 않았습니다.");
        }
        TransportationPreferenceDTO transportation = (TransportationPreferenceDTO) session.getAttribute("transportation");
        if (transportation == null) {
            throw new IllegalStateException("교통 선택이 되지 않았습니다.");
        }
        
        Integer landPrice = (Integer) session.getAttribute("landPrice");
        if (landPrice == null) {
            throw new IllegalStateException("토지 지가 선택이 되지 않았습니다.");
        }
        String industry = (String) session.getAttribute("industry");
        if (industry == null) {
            throw new IllegalStateException("업종 선택이 되지 않았습니다.");
        }

        // DTO 객체로 설문조사 데이터 통합
        RecommendationSurveyDTO surveyDTO = new RecommendationSurveyDTO(region, transportation, landPrice, industry, product, rawMaterial);
        
        // 파이썬 서버로 데이터 전송(RestTemplate을 사용)
        String pythonResponse = recommendationSurveyService.sendToPythonServer(surveyDTO);
        System.out.println("파이썬 서버 응답: " + pythonResponse); // 파이썬 응답 확인. JSON 형식 10개 들어와야 함

        // JSON 응답을 SurveyResultDTO[]로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        SurveyResultDTO[] surveyResults = objectMapper.readValue(pythonResponse, SurveyResultDTO[].class);

        ///////////////////////////////////////////////////////////////////////
        // 임시 이메일(혹시나 유저 이메일을 못가져오는 경우)
        String testUserEmail = "sweeper31113@gmail.com";
        if (oAuth2User != null) {
            testUserEmail = oAuth2User.getAttribute("email"); // 이메일 가져오기 // 잘 가져와짐
        } else {
            testUserEmail = "sweeper31113@gmail.com";
        }
        ////////////////////////////////////////////////////////////////////////
        
        // 설문조사 결과 DB에 저장
        for (SurveyResultDTO result : surveyResults) {
            result.setUserEmail(testUserEmail);
            recommendationSurveyService.saveSurveyResult(result);
        }

        // 파이썬 서버 응답을 세션에 저장
        if (pythonResponse == null || pythonResponse.isEmpty()) {
            System.out.println("Error: Python server response is empty.");
        } else {
            System.out.println("Python server response saved in session: " + pythonResponse);
        }
        session.setAttribute("surveyResult", pythonResponse);

        // 로딩 페이지로 이동
        return "recommendation_result/mainresult";
    }
    
    
    // 결과 페이지로 이동
    @GetMapping("/mainresult")
    public String showMainResult(Model model) {
        // DB에서 가장 최근 10개의 설문조사 결과를 가져옴
        List<SurveyResultEntity> recentResults = surveyResultRepository.findTop10ByOrderByCreateTimeDesc();
        
        // 모델에 결과를 추가하여 뷰로 전달
        model.addAttribute("recentResults", recentResults);
        
        return "recommendation_result/mainresult";
    }
   
}
