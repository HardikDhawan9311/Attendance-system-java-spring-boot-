package com.Attendance.Attendance.System.Teachers.Entity;

import jakarta.persistence.*;

@Entity

@Table(name = "teachers_info")

public class TeachersEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private  int teacherId;

        private String teacherName;

        private int salary;
        private int age;

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


