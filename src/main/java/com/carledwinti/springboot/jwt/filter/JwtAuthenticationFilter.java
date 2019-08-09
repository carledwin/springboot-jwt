package com.carledwinti.springboot.jwt.filter;

import static com.carledwinti.springboot.jwt.model.Constants.HEADER_STRING;
import static com.carledwinti.springboot.jwt.model.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.carledwinti.springboot.jwt.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		String header = httpServletRequest.getHeader(HEADER_STRING);
		String username = null;
		String authToken = null;
		
		if(header != null && header.startsWith(TOKEN_PREFIX)) {
			
			authToken = header.replace(TOKEN_PREFIX, "");
			
			try {
				
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("Argumento ilegal!", e);
			} catch (ExpiredJwtException e) {
				logger.error("JWT expirado!", e);
			} catch (SignatureException e) {
				logger.error("Erro na assinatura!", e);
			} catch (Exception e) {
				logger.error("Erro genérico!", e);
			}
		}else {
			logger.warn("Não foi encontrado Bearer string!");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(jwtTokenUtil.validateToken(authToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				
				logger.info("Usuário >>> " + username + " autenticado setting security context..." );
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				logger.info("Context de seguranca inicializado com sucesso." );
			}
		}
		
		filterChain.doFilter(httpServletRequest, httpServletResponse);
		
	}

}
