package com.Attendance.Attendance.System.Teachers.Service;

import com.Attendance.Attendance.System.Teachers.Entity.TeachersEntity;

import java.util.List;



    public interface TeachersService {
        public TeachersEntity create(TeachersEntity teachers);
        public List<TeachersEntity> getAll();
        public TeachersEntity getById (int id);
        public TeachersEntity update (TeachersEntity teachers,int id);
        public void delete (int id);
    }

