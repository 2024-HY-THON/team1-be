package com.example.teamOnebe.SpringSecurity.jwt;

import com.example.teamOnebe.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String auth = request.getHeader("Authorization");

        //토큰이 없는경우
        if (auth == null || !auth.startsWith("Bearer "))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //토큰없는경우 401반환
            response.getWriter().write("Token does not exist");
            return;
        }

        String token = auth.split(" ")[1];
        //만료된 경우
        if(jwtUtil.isExpired(token))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //만료된 경우 401반환
            response.getWriter().write("Token expired");
            return;
        }

        //토큰에서 id role가져옴
        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);

        //pw는 임의로
        User user = User.builder()
                    .username(username)
                    .password("temp")
                    .role(role)
                    .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //시큐리티 인증토큰
        Authentication authToekn = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //서버세션에 사용자등록
        SecurityContextHolder.getContext().setAuthentication(authToekn);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.equals("/login") || path.equals("/") || path.equals("/register") || path.equals("/usernameVerify"); // 세 가지 경우는 필터를 거치지 않도록 예외처리
    }
}
