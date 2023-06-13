package com.jwt.example.Controllers;

import com.jwt.example.Models.User;
import com.jwt.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")       //http://localhost:8081/home/user user:cff927a4-3b45-4a8e-b951-871280b96103
    public List<User> getUser(){
        System.out.println("Getting User");

        return userService.getUser();
    }

    @GetMapping("/currentUser")
    public String getLoggedInUser(Principal principal){
        System.out.println("Getting current user:"+principal.getName());
        return principal.getName();
    }
}
