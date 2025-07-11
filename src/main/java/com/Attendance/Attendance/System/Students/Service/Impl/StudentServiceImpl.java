package com.Attendance.Attendance.System.Students.Service.Impl;

import com.Attendance.Attendance.System.Students.Entity.StudentEntity;
import com.Attendance.Attendance.System.Students.Repository.StudentRepo;
import com.Attendance.Attendance.System.Students.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException; // Import for data access exceptions
import java.util.List;
import java.util.Optional; // Import for Optional

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Override // It's good practice to add @Override for implemented methods
    public StudentEntity create(StudentEntity students){
        try {
            return studentRepo.save(students);
        } catch (DataAccessException e) {
            // Log the specific data access exception
            System.err.println("Error saving student to database: " + e.getMessage());
            // Re-throw as a generic RuntimeException or a custom exception
            throw new RuntimeException("Failed to create student due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while creating student: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during student creation.", e);
        }
    }

    @Override
    public List<StudentEntity> getAll(){
        try {
            return studentRepo.findAll();
        } catch (DataAccessException e) {
            System.err.println("Error retrieving all students from database: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve students due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching all students: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during student retrieval.", e);
        }
    }

    @Override
    public StudentEntity getById(int id){
        try {
            Optional<StudentEntity> studentOptional = studentRepo.findById(id);
            return studentOptional.orElse(null);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving student with ID " + id + " from database: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve student with ID " + id + " due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching student with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during student retrieval.", e);
        }
    }

    @Override
    public StudentEntity update(StudentEntity students, int id){
        try {
            StudentEntity newStudent = studentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found")); // This runtime exception is caught in controller

            newStudent.setStudentName(students.getStudentName());
            newStudent.setGrade(students.getGrade());
            newStudent.setAge(students.getAge());

            return studentRepo.save(newStudent);
        } catch (DataAccessException e) {
            System.err.println("Error updating student with ID " + id + " in database: " + e.getMessage());
            throw new RuntimeException("Failed to update student with ID " + id + " due to a database error.", e);
        } catch (RuntimeException e) {
            // Re-throw "Student not found" runtime exception for the controller to handle specifically
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating student with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during student update.", e);
        }
    }

    @Override
    public void delete (int id){
        try {
            // Check if the student exists before attempting to delete (optional, but good practice)
            if (!studentRepo.existsById(id)) {
                throw new RuntimeException("Student with ID " + id + " not found for deletion.");
            }
            studentRepo.deleteById(id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting student with ID " + id + " from database: " + e.getMessage());
            throw new RuntimeException("Failed to delete student with ID " + id + " due to a database error.", e);
        } catch (RuntimeException e) {
            // Re-throw "Student not found" if added above
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while deleting student with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during student deletion.", e);
        }
    }
}