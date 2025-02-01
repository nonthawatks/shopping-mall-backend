package com.example.meeboilerplate.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.meeboilerplate.service.impl.TokenServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;

public class TokenFilter extends GenericFilterBean {
    private final TokenServiceImpl tokenService;

    public TokenFilter(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if(ObjectUtils.isEmpty(authorization)){
            chain.doFilter(request,response);
            return;
        }

        if(!authorization.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }

        String token = authorization.substring(7);

        DecodedJWT decoded = tokenService.validateToken(token);

        if(decoded == null){
            chain.doFilter(request,response);
            return;
        }

        Integer userId = decoded.getClaim("userId").asInt();

        ArrayList<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("user"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, "(protected)", authorityList);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }
}
