package com.kdigital.factoryPick.service;

import java.time.LocalDateTime;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.config.CustomOauth2UserDetails;
import com.kdigital.factoryPick.config.GoogleUserDetails;
import com.kdigital.factoryPick.config.KakaoUserDetails;
import com.kdigital.factoryPick.config.NaverUserDetails;
import com.kdigital.factoryPick.config.OAuth2UserInfo;
import com.kdigital.factoryPick.entity.UserEntity;
import com.kdigital.factoryPick.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession session;  
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 loadUser 메서드 호출하여 OAuth2 사용자 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2 User Attributes: {}", oAuth2User.getAttributes());

        // 클라이언트 등록 정보 가져오기 (예: google)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = extractUserInfo(provider, oAuth2User);

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String snsType = provider;
        String name = oAuth2UserInfo.getName();

        // 사용자 정보 확인 및 생성 (없으면 새로 생성)
        UserEntity user = userRepository.findByUserEmail(email)
                .orElseGet(() -> createUser(email, name, snsType, providerId));

        log.info("Loaded user with principalName (email): {}", user.getPrincipalName());

     // **로그인 성공 시 세션에 인증 정보 저장**
        session.setAttribute("authenticated", true);
        session.setAttribute("user", user);  // 사용자 정보를 세션에 저장

        
        // 커스텀 OAuth2User 반환
        return new CustomOauth2UserDetails(user, oAuth2User.getAttributes(), user.getPrincipalName());
    }

    /**
     * 소셜 로그인 사용자 정보를 추출하는 메서드
     */
    private OAuth2UserInfo extractUserInfo(String provider, OAuth2User oAuth2User) {
        switch (provider) {
            case "google":
                log.info("Google Login Successful");
                return new GoogleUserDetails(oAuth2User.getAttributes());
            case "kakao":
            	log.info("카카오 로그인");
            	return new KakaoUserDetails(oAuth2User.getAttributes());
            case "naver":
            	log.info("네이버 로그인");
            	return new NaverUserDetails(oAuth2User.getAttributes());
            default:
                throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }
    }

    /**
     * 새 사용자 생성 메서드
     */
    private UserEntity createUser(String email, String name, String snsType, String providerId) {
        UserEntity user = UserEntity.builder()
                .userEmail(email)
                .userName(name)
                .snsType(snsType)
                .providerId(providerId)
                .userRole("ROLE_USER") // 기본 권한 부여
                .userPhone("unknown") // 기본값 설정
                .createdTime(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
}