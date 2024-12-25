package com.kdigital.factoryPick.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
		.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/index", "/index/**","/login/**","/api/auth-status" ).permitAll()
				.requestMatchers("/mypage/**", "/recommendation/**").authenticated()
				.anyRequest().permitAll()
				);

		// 폼 로그인 설정
		// OAuth 2.0 로그인 설정 (폼 로그인 없이)
		http
		.oauth2Login((auth) -> auth
				.loginPage("/login")  // 로그인 페이지 경로
				.successHandler(new CustomLoginSuccessHandler())
				.failureUrl("/login?error=true")  // 로그인 실패 시
				.permitAll()
				);

		// 로그아웃 설정
		http
		.logout((auth) -> auth
			    .logoutUrl("/logout")  // 로그아웃 URL 설정
			    .logoutSuccessHandler((request, response, authentication) -> {
			        HttpSession session = request.getSession(false);  // 세션이 있으면 가져옴
			        if (session != null) {
			            session.invalidate();  // 세션 무효화
			        }
			        // JSESSIONID 쿠키를 명시적으로 삭제
			        Cookie cookie = new Cookie("JSESSIONID", null);
			        cookie.setPath("/");
			        cookie.setHttpOnly(true);
			        cookie.setMaxAge(0);  // 쿠키 만료 설정
			        response.addCookie(cookie);  // 쿠키 삭제 적용

			        response.sendRedirect("/login?logout=true");  // 로그아웃 후 리다이렉트
			    })
			    .deleteCookies("JSESSIONID")  // 기본 설정으로도 쿠키 삭제 시도
			    .permitAll()
				)
        .sessionManagement(session -> session
                .sessionFixation().newSession()
                .invalidSessionUrl("/login?session=expired")
                .maximumSessions(1)
                .expiredUrl("/login?session=expired")
            )
		.headers(headers -> headers.frameOptions().sameOrigin());  // X-Frame-Options 설정
		;

		http.csrf().disable();  // 개발 환경에서 CSRF 비활성화

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// 로그인 성공 시 세션에 저장된 경로로 리다이렉트하는 핸들러
	public static class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                        Authentication authentication) throws IOException {
	        HttpSession session = request.getSession();
	        String redirectUri = (String) session.getAttribute("redirectUri");

	        if (redirectUri == null) {
	            // 세션 스토리지에 저장된 경로를 사용
	            redirectUri = request.getParameter("redirectPath");
	        }

	        if (redirectUri != null) {
	            session.removeAttribute("redirectUri"); // 세션에서 경로 제거
	            getRedirectStrategy().sendRedirect(request, response, redirectUri); // 해당 경로로 리다이렉트
	        } else {
	            getRedirectStrategy().sendRedirect(request, response, "/"); // 기본 경로로 리다이렉트
	        }
	    }
	}
}




