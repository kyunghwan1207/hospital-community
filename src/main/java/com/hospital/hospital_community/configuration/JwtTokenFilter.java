package com.hospital.hospital_community.configuration;

import com.hospital.hospital_community.domain.entity.User;
import com.hospital.hospital_community.domain.utils.JwtUtil;
import com.hospital.hospital_community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token은 변경되면 안되기에 final키워드 사용
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizatonHeader: {}", authorizationHeader);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            log.error("토큰이 없거나 형식이 맞지 않습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // token 분리
        String token;
        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e){
            log.error("toekn 추출에 실패했습니다");
            filterChain.doFilter(request, response);
            return;
        }
        // token 유효한지 check
        if(JwtUtil.isExpired(token, secretKey)){
            log.info("token이 유효하지 않습니다");
            filterChain.doFilter(request, response);
            return;
        }
        String email =  JwtUtil.getEmail(token, secretKey);
        log.info("try access user's email = " + email);
        User findUser = userService.getUserByEmail(email);
        log.info("findUser's role = " + findUser.getRole());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))); // 문열어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 다음인증에 필요한 정보를 넘김
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 권한 부여
        filterChain.doFilter(request, response);
    }
}
