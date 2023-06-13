package com.jwt.example.Controllers;

import com.jwt.example.Models.JWTRequest;
import com.jwt.example.Models.JWTResponse;
import com.jwt.example.Security.JWTHelper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    //to allow login and authentication.
    @Autowired
    UserDetailsService userDetailsService;  //In App config

    @Autowired
    AuthenticationManager authenticationManager;    //In App Config

    @Autowired
    private JWTHelper jwtHelper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){

        this.doAuthentication(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthentication(String email, String password) {

        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(authenticate);
        }catch (BadCredentialsException b){
            throw new RuntimeException("Inavalid Credentials..!!");
        }
    }
}
