package com.Attendance.Attendance.System.UserAuth.Controller;

import com.Attendance.Attendance.System.UserAuth.Entity.UsersEntity;
import com.Attendance.Attendance.System.UserAuth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> Signup(@RequestBody UsersEntity request){
        String result=userService.Signup(request);
        return ResponseEntity.ok(result);
    }
}
