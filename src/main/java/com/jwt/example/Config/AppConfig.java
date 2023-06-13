package com.jwt.example.Config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

        //INMemory Authentication using spring boot security.
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User.builder().username("sudhanshu").password(passwordEncoder().encode("possword")).roles("ADMIN").build();
        UserDetails user1 = User.builder().username("mahavir").password(passwordEncoder().encode("password")).roles("Manager").build();

        return new InMemoryUserDetailsManager(user, user1);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
