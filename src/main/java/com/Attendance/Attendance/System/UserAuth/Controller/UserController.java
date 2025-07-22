package com.Attendance.Attendance.System.UserAuth.Controller;

import com.Attendance.Attendance.System.Config.JwtUtil;
import com.Attendance.Attendance.System.UserAuth.Entity.UsersEntity;
import com.Attendance.Attendance.System.UserAuth.Service.CustomUserDetailsService;
import com.Attendance.Attendance.System.UserAuth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UsersEntity request) {
        try {
            // ✅ Save user
            String result = userService.Signup(request);

            // ✅ Authenticate new user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // ✅ Use CustomUserDetailsService to load user details
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

            // ✅ Generate token
            String jwtToken = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok("Signup successful. Bearer " + jwtToken);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Signup failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersEntity request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
            String jwtToken = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok("Bearer " + jwtToken);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
