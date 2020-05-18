package com.example.newTest.security;

import com.example.newTest.dto.TokenAndStatus;
import com.example.newTest.dto.UsernamePassword;
import com.example.newTest.entity.UserInfo;
import com.example.newTest.repositories.UserInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.newTest.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.newTest.security.SecurityConstants.SECRET;
import static com.example.newTest.security.SecurityConstants.HEADER_STRING;
import static com.example.newTest.security.SecurityConstants.TOKEN_PREFIX;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationManager authenticationManager;
    private UserInfoRepository userInfoRepository;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePassword user_info = new ObjectMapper().readValue(request.getInputStream(), UsernamePassword.class);
            System.out.println(user_info);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user_info.getUsername(),
                            user_info.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException el) {
            throw new RuntimeException(el);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if(userInfoRepository==null){
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userInfoRepository = webApplicationContext.getBean(UserInfoRepository.class);
        }

        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
//        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        TokenAndStatus tokenAndStatus = new TokenAndStatus();
        tokenAndStatus.setToken(TOKEN_PREFIX + token);
        UserInfo userInfo = userInfoRepository.findByUsername(authResult.getName());
        tokenAndStatus.setStatus(userInfo.getStatus());
        tokenAndStatus.setId(userInfo.getId());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenAndStatus);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().print(tokenJsonResponse);

    }
}
