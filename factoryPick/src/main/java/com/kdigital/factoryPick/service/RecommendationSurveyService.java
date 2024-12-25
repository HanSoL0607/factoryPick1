package com.kdigital.factoryPick.service;

import com.kdigital.factoryPick.dto.RecommendationSurveyDTO;

import com.kdigital.factoryPick.dto.SurveyResultDTO;
import com.kdigital.factoryPick.entity.SurveyResultEntity;
import com.kdigital.factoryPick.repository.SurveyResultRepository;

import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class RecommendationSurveyService {
	
    // Repository 주입
    @Autowired
    private SurveyResultRepository surveyResultRepository;

	// 스프링 제공 HTTP 클라이언트, 파이썬 서버로 HTTP 요청 보내기 위함
    private final RestTemplate restTemplate;	

    // 파이썬 서버의 URL을 외부 설정 파일 (application.properties)에서 받아옴
    @Value("${python.server.url}")
    private String pythonServerUrl;

    public RecommendationSurveyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 파이썬 서버로 설문조사 데이터를 전송하는 메서드
    public String sendToPythonServer(RecommendationSurveyDTO surveyDTO) {
        // HTTP 요청 헤더 설정 (JSON 형식)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);	// JSON 파일 주고받기
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));	// UTF-8 인코딩

        // DTO 객체 본문에 포함
        HttpEntity<RecommendationSurveyDTO> request = new HttpEntity<>(surveyDTO, headers);

        // 파이썬 서버로 POST 요청 보내기
        try {
            // 파이썬 서버로 POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(
                    pythonServerUrl + "/recommendation",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // 응답 본문이 비어있는지 확인
            if (response.getBody() == null || response.getBody().isEmpty()) {
                System.out.println("Error: Received empty response from Python server.");
                return null;
            } else {
                System.out.println("Received response from Python server: " + response.getBody());
                return response.getBody();
            }
        } catch (Exception e) {
            System.out.println("Error: Exception while connecting to Python server: " + e.getMessage());
            return null;
        }
    }
    
    // 파이썬 서버로부터 받은 결과를 저장하는 메서드
    public void saveSurveyResult(SurveyResultDTO surveyResultDTO) {
        // DTO -> Entity 변환
        SurveyResultEntity surveyResultEntity = SurveyResultDTO.toEntity(surveyResultDTO);

        // DB에 저장
        surveyResultRepository.save(surveyResultEntity);
    }
    
 // 결과 페이지로 이동
    @GetMapping("/mainresult")
    public String showMainResult(HttpSession session, Model model) {
        // 세션에서 파이썬 서버 응답 데이터를 가져옴
        String surveyResultJson = (String) session.getAttribute("surveyResult");
        
        // surveyResultJson이 null인지 확인하는 로그 출력
        if (surveyResultJson == null || surveyResultJson.isEmpty()) {
            System.out.println("Error: surveyResultJson is null or empty.");
        } else {
            System.out.println("surveyResultJson found: " + surveyResultJson);
        }

        // 데이터를 모델에 추가하여 뷰로 전달
        model.addAttribute("surveyResultJson", surveyResultJson);

        return "recommendation_result/mainresult";
    }
    
}