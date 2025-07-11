package com.Attendance.Attendance.System.UserAuth.Service.impl;

import com.Attendance.Attendance.System.UserAuth.Entity.UsersEntity;
import com.Attendance.Attendance.System.UserAuth.Repository.User_Auth_Repository;
import com.Attendance.Attendance.System.UserAuth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private User_Auth_Repository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String Signup(@RequestBody UsersEntity request){
        if(userAuthRepository.findByUsername(request.getUsername()).isPresent()){
            return "Username is Already Exist";
        }

        UsersEntity user=new UsersEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        userAuthRepository.save(user);

        return "User is Registered Sucessfully";

    }
}
