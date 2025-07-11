package com.Attendance.Attendance.System.Students.Repository;

import com.Attendance.Attendance.System.Students.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Integer> {
}