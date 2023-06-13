package com.jwt.example.Services;

import com.jwt.example.Models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private List<User> user = new ArrayList<>();

    public UserService(){
        //Adding 5 use details here.
        user.add(new User(UUID.randomUUID().toString(),"Sudhanshu","sudhu@mail.com"));
        user.add(new User(UUID.randomUUID().toString(),"Rahul","rahul@mail.com"));
        user.add(new User(UUID.randomUUID().toString(),"Sourav","sourav@mail.com"));
        user.add(new User(UUID.randomUUID().toString(),"Mahavir","mahavir@mail.com"));
        user.add(new User(UUID.randomUUID().toString(),"Abhishek","abhishek@mail.com"));
    }

    public List<User> getUser() {
        return this.user;
    }
}
