package com.example.hyppp.SpringSecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        //json으로 요청시
        if(request.getContentType().equals("application/json"))
        {
            try{
                Map credentials = objectMapper.readValue(request.getInputStream(),Map.class);
                String username = credentials.get("username").toString();
                String password = credentials.get("password").toString();
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
                return authenticationManager.authenticate(authRequest);
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        //x-www-form-urlencoded
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        System.out.println(username);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, null);

        //authManager에게 넘겨줌->userdetails사용
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
    {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        String username = customUserDetails.getUsername();
        String role = "ROLE_USER";

        //권한이 여러개라면 아래 코드 고려
//        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority authority = iterator.next();
//        String role = authority.getAuthority();

        String token = jwtUtil.generateJwt(username,role,1000*60*60L); //1시간

        //HTTP RFC 7235
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
    {
        if(failed instanceof BadCredentialsException)//401
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else if(failed instanceof InternalAuthenticationServiceException)//서버오류
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//그외
        }
    }

}

