package com.Attendance.Attendance.System.UserAuth.Repository;


import com.Attendance.Attendance.System.UserAuth.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User_Auth_Repository extends JpaRepository<UsersEntity,Integer>{
    Optional<UsersEntity> findByUsername(String username);
}
