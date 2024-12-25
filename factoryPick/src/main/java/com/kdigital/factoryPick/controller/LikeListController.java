package com.kdigital.factoryPick.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdigital.factoryPick.service.LikeListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // API 응답을 위해 @RestController 사용
public class LikeListController {


   private final LikeListService likeListService;

   // LikeListService 주입 (생성자 주입 방식)
   public LikeListController(LikeListService likeListService) {
      this.likeListService = likeListService;
   }
   @GetMapping("/property")
   public String property(@AuthenticationPrincipal OAuth2User oAuth2User,
		   @RequestParam(value = "search", required = false) String search, 
		   HttpServletRequest request,
		   Model model) {
       if (oAuth2User != null) {
           String username = oAuth2User.getAttribute("name");	// 로그인 유저 id 가져오기
           model.addAttribute("username", username);
       }
       if (search != null && !search.isBlank()) {
           model.addAttribute("searchValue", search);  // 검색어 전달
       }
       
       // 세션에 저장된 리다이렉트 경로가 있는지 확인
       HttpSession session = request.getSession();
       String redirectUri = (String) session.getAttribute("redirectUri");

       if (redirectUri != null) {
           session.removeAttribute("redirectUri"); // 세션에서 경로 제거
           return "redirect:" + redirectUri; // 원래 경로로 리다이렉트
       }
       
      return "property/property";
   }
   @PostMapping("/api/like")
   @ResponseBody  // AJAX 요청에 대한 JSON 응답을 위해 추가
   public Map<String, Object> handleLike(
           @RequestBody Map<String, String> requestBody,  // JSON 형태로 요청 받음
           @AuthenticationPrincipal OAuth2User oAuth2User,
           HttpServletRequest request) {

       String complexName = requestBody.get("complexName");

       if (oAuth2User == null) {
           String currentPath = request.getHeader("Referer");
           request.getSession().setAttribute("redirectUri", currentPath); // 경로 저장
           System.out.println("LIkeLIstController: " + currentPath);
           return Map.of("success", false, "message", "로그인 후 이용바랍니다!");
       }

       String userEmail = oAuth2User.getAttribute("email");
       if (userEmail == null || userEmail.isBlank()) {
           return Map.of("success", false, "message", "유효한 이메일이 필요합니다.");
       }
       // 이미 찜한 경우
       if (likeListService.isLikedByUser(userEmail, complexName)) {
           return Map.of("success", false, "message", "이미 찜하기 목록에 있는 산업단지입니다 :)");
       }
       
       likeListService.saveLike(userEmail, complexName);
       return Map.of("success", true, "username", userEmail);
   }
   
   
   
   // 사용자 로그인 상태 확인 API
   @GetMapping("/api/user/status")
   @ResponseBody
   public Map<String, Object> userStatus(@AuthenticationPrincipal OAuth2User oAuth2User) {
       Map<String, Object> response = new HashMap<>();
       response.put("loggedIn", oAuth2User != null);
       return response;
   }
}

