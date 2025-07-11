package com.Attendance.Attendance.System.Teachers.Service.Impl;

import com.Attendance.Attendance.System.Teachers.Entity.TeachersEntity;
import com.Attendance.Attendance.System.Teachers.Repository.TeachersRepo;
import com.Attendance.Attendance.System.Teachers.Service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException; // Import for data access exceptions
import java.util.List;
import java.util.Optional; // Import for Optional

@Service
public class TeachersServiceImpl implements TeachersService {

    @Autowired
    private TeachersRepo teacherRepo;

    @Override
    public TeachersEntity create(TeachersEntity teachers){
        try {
            return teacherRepo.save(teachers);
        } catch (DataAccessException e) {
            // Log the specific data access exception
            System.err.println("Error saving teacher to database: " + e.getMessage());
            // Re-throw as a generic RuntimeException or a custom exception
            throw new RuntimeException("Failed to create teacher due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while creating teacher: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during teacher creation.", e);
        }
    }

    @Override
    public List<TeachersEntity> getAll(){
        try {
            return teacherRepo.findAll();
        } catch (DataAccessException e) {
            System.err.println("Error retrieving all teachers from database: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve teachers due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching all teachers: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during teacher retrieval.", e);
        }
    }

    @Override
    public TeachersEntity getById(int id){
        try {
            Optional<TeachersEntity> teacherOptional = teacherRepo.findById(id);
            return teacherOptional.orElse(null);
        } catch (DataAccessException e) {
            System.err.println("Error retrieving teacher with ID " + id + " from database: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve teacher with ID " + id + " due to a database error.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while fetching teacher with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during teacher retrieval.", e);
        }
    }

    @Override
    public TeachersEntity update(TeachersEntity teachers, int id){
        try {
            TeachersEntity newTeacher = teacherRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found")); // This runtime exception is caught in controller

            newTeacher.setTeacherName(teachers.getTeacherName());
            newTeacher.setSalary(teachers.getSalary());
            newTeacher.setAge(teachers.getAge());

            return teacherRepo.save(newTeacher);
        } catch (DataAccessException e) {
            System.err.println("Error updating teacher with ID " + id + " in database: " + e.getMessage());
            throw new RuntimeException("Failed to update teacher with ID " + id + " due to a database error.", e);
        } catch (RuntimeException e) {
            // Re-throw "Teacher not found" runtime exception for the controller to handle specifically
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while updating teacher with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during teacher update.", e);
        }
    }

    @Override
    public void delete (int id){
        try {
            // Check if the teacher exists before attempting to delete (optional, but good practice)
            if (!teacherRepo.existsById(id)) {
                throw new RuntimeException("Teacher with ID " + id + " not found for deletion.");
            }
            teacherRepo.deleteById(id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting teacher with ID " + id + " from database: " + e.getMessage());
            throw new RuntimeException("Failed to delete teacher with ID " + id + " due to a database error.", e);
        } catch (RuntimeException e) {
            // Re-throw "Teacher not found" if added above
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while deleting teacher with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during teacher deletion.", e);
        }
    }
}