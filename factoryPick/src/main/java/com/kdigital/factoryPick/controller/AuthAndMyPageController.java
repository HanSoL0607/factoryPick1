package com.kdigital.factoryPick.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.kdigital.factoryPick.service.LikeListService;

@Controller  // API 응답을 위해 @RestController 사용
public class AuthAndMyPageController {

    // 인증 상태를 반환하는 API
    @GetMapping("/api/auth-status")
    @ResponseBody
    public Map<String, Boolean> authStatus(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = oAuth2User != null;
        response.put("authenticated", isAuthenticated);
        return response;
    }

    private final LikeListService likeListService;

    // LikeListService 주입 (생성자 주입 방식)
    public AuthAndMyPageController(LikeListService likeListService) {
        this.likeListService = likeListService;
    }

    // 마이페이지 매핑: OAuth2 로그인 사용자 정보 활용
    @GetMapping("/mypage/FinalModal")
    public String myPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // OAuth2User로부터 사용자 이메일을 가져옴
        String email = oAuth2User.getAttribute("email");
        System.out.println("로그인한 사용자 이메일:"+ email);
        // 해당 이메일에 대한 모든 complex_name 가져오기
        List<String> complexNames = likeListService.getComplexNamesByUserEmail(email);
        if (complexNames.isEmpty()) {
            System.out.println("조회된 complex_name 데이터가 없습니다.");
        } else {
            System.out.println("해당 사용자의 complex_name 목록:");
            complexNames.forEach(name -> System.out.println("complex_name: " + name));
        }
        
        // 모델에 complex_names 추가
        model.addAttribute("likeList", complexNames);

        return "mypage/FinalModal";
    }
    // 로그아웃 후 리다이렉트 처리 추가
    @GetMapping("/logout")
    public String logout() {
        // 세션 무효화 및 로그아웃 후 메인 페이지로 리다이렉트
        return "redirect:/";
    }    
    
}
