package com.project.bridgetalkbackend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bridgetalkbackend.Service.LoginService;
import com.project.bridgetalkbackend.dto.CustomUserDetails;
import com.project.bridgetalkbackend.dto.LoginDTO;
import com.project.bridgetalkbackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JWTUtil jwtUtil,LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.loginService = loginService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 추출
        try {
            LoginDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            request.setCharacterEncoding("UTF-8");
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            //스프링 시큐리티에서 email과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

            //token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String email = customUserDetails.getUsername();
        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("access", email, role, 600000L);   // 10분
        String refresh = jwtUtil.createJwt("refresh", email, role, 86400000L); //24시간

//        refreshService.addRefresh(email, role, 86400000L);

        response.addHeader("Authorization", "Bearer " + access);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginService.loginSuccess(email)));
    }
    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //로그인 실패시 401 응답 코드 반환
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"message\": \"아이디 또는 비밀번호가 다릅니다.: " + "\"}");
    }

    // 쿠키 생성 메소드
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true); //https 할 때
        //cookie.setPath("/"); //경로 범위 설정 가능
        cookie.setHttpOnly(true); //자바스크립트로 접근X
        return cookie;
    }
}
