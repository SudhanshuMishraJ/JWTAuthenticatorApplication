package com.jwt.example.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Authorization = bearer token
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header:{}", requestHeader);

        String username = null;
        String token = null;

        if(requestHeader !=null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7); //Bearer is of length 6, we need data after it ends.
            try{
                username = this.jwtHelper.getUsernameFromToken(token);

            }catch (IllegalArgumentException e){
                logger.error("Illegal argument while fetching username!!!");
                e.printStackTrace();
            }catch (ExpiredJwtException j){
                logger.error("Given JWT Token is expired");
                j.printStackTrace();
            }catch (MalformedJwtException m){
                logger.error("Something changed.Invalid token!!");
                m.printStackTrace();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            logger.info("Invalid header value");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //fetch user details from username.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if(validateToken){

                //Authentication
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else{
                logger.info("Validation Failed..!!");
            }
        }
        //forwarding the request one validation of token is done.
        filterChain.doFilter(request,response);

    }
}
