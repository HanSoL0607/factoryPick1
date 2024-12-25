package com.kdigital.factoryPick.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdigital.factoryPick.dto.ComplexBasicDTO;
import com.kdigital.factoryPick.service.LikeListService;
import com.kdigital.factoryPick.service.RecommendListService;

@Controller
public class RecommendListController {
    private final RecommendListService recommendListService;

    public RecommendListController(RecommendListService recommendListService) {
        this.recommendListService = recommendListService;
}

    // 마이페이지 매핑: OAuth2 로그인 사용자 정보 활용
    @GetMapping("/mypage/mypage_recommend")
    public String myPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // OAuth2User로부터 사용자 이메일을 가져옴
        String email = oAuth2User.getAttribute("email");
        System.out.println("로그인한 사용자 이메일:"+ email);
        // 해당 이메일에 대한 모든 complex_name 가져오기
        List<String> complexNames = recommendListService.getComplexNamesByUserEmail(email);
        if (complexNames.isEmpty()) {
            System.out.println("조회된 complex_name 데이터가 없습니다.");
        } else {
            System.out.println("해당 사용자의 complex_name 목록:");
            complexNames.forEach(name -> System.out.println("complex_name: " + name));
        }
        
        // 모델에 complex_names 추가
        model.addAttribute("likeList", complexNames);
        
        // ComplexBasic 파일에서 가져온 주소 목록들 
        List<ComplexBasicDTO> complexDTOList = recommendListService.getComplexAddressesByNames(complexNames);
        if (complexDTOList.isEmpty()) {
            System.out.println("조회된 address 데이터가 없습니다.");
        } else {
            System.out.println("해당 사용자의 address 목록:");
            complexDTOList.forEach(name -> System.out.println("주소: " + name));
        }
        model.addAttribute("complexList", complexDTOList);
        return "mypage/mypage_recommend";
    }
}

