package com.Attendance.Attendance.System.Students.Service;

import com.Attendance.Attendance.System.Students.Entity.StudentEntity;

import java.util.List;



public interface StudentService {
    public StudentEntity create(StudentEntity students);
    public List<StudentEntity> getAll();
    public StudentEntity getById (int id);
    public StudentEntity update (StudentEntity students,int id);
    public void delete (int id);
}