package com.Attendance.Attendance.System.UserAuth.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "UserAuthInfo")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "UsersUsername",nullable = false,unique = true)
    private String username;

    @Column(name = "UsersPassword",nullable = false)
    private String password;

    @Column(name = "UsersRole",nullable = false)
    private String roles;
}
