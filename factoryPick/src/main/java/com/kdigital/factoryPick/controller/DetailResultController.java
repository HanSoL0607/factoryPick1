package com.kdigital.factoryPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kdigital.factoryPick.dto.TransInfoDTO;
import com.kdigital.factoryPick.service.ComplexInfoService;
import com.kdigital.factoryPick.service.ComplexRatioService;
import com.kdigital.factoryPick.service.TransInfoService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.kdigital.factoryPick.dto.ComplexInfoDTO;
import com.kdigital.factoryPick.dto.ComplexRatioDTO;

import com.kdigital.factoryPick.service.IntegrationService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;

@Controller
@RequiredArgsConstructor
public class DetailResultController {

   private final ComplexInfoService complexInfoService;
   private final ComplexRatioService complexRatioService;
   private final TransInfoService transInfoService;
   
   // 세제혜택 PDF 다운로드 메서드
   @GetMapping("/download-pdf")
   public ResponseEntity<InputStreamResource> downloadPdf() {
       try {
           // PDF 파일 로드
           ClassPathResource pdfFile = new ClassPathResource("static/pdf/benefits.pdf");

           // InputStreamResource로 변환
           InputStreamResource resource = new InputStreamResource(pdfFile.getInputStream());

           // HTTP 헤더 설정 (파일 다운로드용)
           HttpHeaders headers = new HttpHeaders();
           headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=benefits.pdf");

           // 파일 반환
           return ResponseEntity.ok()
                   .headers(headers)
                   .body(resource);  // InputStreamResource는 Resource의 하위 클래스이므로 사용 가능

       } catch (IOException e) {
           e.printStackTrace();  // 오류 로그 출력
           return ResponseEntity.status(500).build();  // 오류 발생 시 500 반환
       }
   }

   @GetMapping("/detailresult")
   public String detailResult(@RequestParam("name") String name, Model model) {
      
      
       // 1. 교통 정보 조회
        TransInfoDTO transInfoDTO = transInfoService.getComplexInfoByName(name);
        if (transInfoDTO != null) {
            // 고속도로 정보 연결 (이름과 거리 합침)
            String highwayInfo = transInfoDTO.getHighwayName() + " (" + transInfoDTO.getHighwayDistance() + "km)";
            String airportInfo = transInfoDTO.getAirportName() + " (" + transInfoDTO.getAirportDistance() + "km)";
            String seaportInfo = transInfoDTO.getSeaportName() + " (" + transInfoDTO.getSeaportDistance() + "km)";
            String stationInfo = transInfoDTO.getStationName() + " (" + transInfoDTO.getStationDistance() + "km)";

            // HTML에 데이터 전달
            model.addAttribute("highwayInfo", highwayInfo);
            model.addAttribute("airportInfo", airportInfo);
            model.addAttribute("seaportInfo", seaportInfo);
            model.addAttribute("stationInfo", stationInfo);
            model.addAttribute("transInfoDTO", transInfoDTO);
        } else {
            model.addAttribute("transError", "교통 정보를 찾을 수 없습니다.");
        }

        // 2. 유치업종 정보 조회
        ComplexInfoDTO complexInfoDTO = complexInfoService.getComplexInfoByName(name);
        if (complexInfoDTO != null) {
           System.out.println("Avg Price: " + complexInfoDTO.getAvgPrice()); // 평균 분양가 확인용 로그
            model.addAttribute("complexInfoDTO", complexInfoDTO);
        } else {
            model.addAttribute("industryError", "유치업종 정보를 찾을 수 없습니다.");
        }
        
        
        // 3. 업종비율 정보 조회
        ComplexRatioDTO complexRatioDTO = complexRatioService.getComplexRatioByComplexName(name);
        if (complexRatioDTO != null) {
            model.addAttribute("complexRatioData", complexRatioDTO);
        } else {
            model.addAttribute("error", "업종 집적도 데이터를 찾을 수 없습니다.");
        }
        
        // 4. 산업단지의 웹사이트 URL 조회 및 오류 처리
        if (complexInfoDTO != null && complexInfoDTO.getWebsiteUrl() != null) {
            String websiteUrl = complexInfoDTO.getWebsiteUrl();
            model.addAttribute("websiteUrl", websiteUrl);
        } else {
            model.addAttribute("urlError", "해당 산업단지의 URL을 찾을 수 없습니다.");
            model.addAttribute("websiteUrl", "");  // 빈 문자열로 초기화
        }

        // 5. 결과 페이지로 이동
        return "recommendation_result/detailresult";
    }
}