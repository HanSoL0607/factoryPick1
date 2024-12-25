package com.kdigital.factoryPick.controller;
import com.kdigital.factoryPick.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
	   private final HttpSession session;

	    @GetMapping("/api/like/user-status")
	    public Map<String, Object> getUserStatus() {
	        Map<String, Object> response = new HashMap<>();
	        
	        // 세션에 저장된 사용자 정보 확인
	        UserEntity user = (UserEntity) session.getAttribute("user");
	        Boolean isAuthenticated = (Boolean) session.getAttribute("authenticated");

	        response.put("authenticated", isAuthenticated != null && isAuthenticated);
	        
	        if (user != null) {
	            response.put("userEmail", user.getUserEmail());
	            response.put("userName", user.getUserName());
	            response.put("snsType", user.getSnsType());
	        } else {
	            response.put("userEmail", null);
	            response.put("userName", null);
	            response.put("snsType", null);
	        }

	        return response;
	    }
}
