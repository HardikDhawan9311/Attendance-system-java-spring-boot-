package com.Attendance.Attendance.System.Teachers.Repository;

import com.Attendance.Attendance.System.Teachers.Entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachersRepo extends JpaRepository<TeachersEntity, Integer> {
}
