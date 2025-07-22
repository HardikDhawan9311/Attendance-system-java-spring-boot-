package com.Attendance.Attendance.System.Students.Controllers;

import com.Attendance.Attendance.System.Students.Entity.StudentEntity;
import com.Attendance.Attendance.System.Students.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('TEACHER','PRINCIPAL')")
    public ResponseEntity<Map<String, String>> create(@RequestBody StudentEntity students) {
        Map<String, String> response = new HashMap<>();
        try {
            StudentEntity createdStudent = studentService.create(students);
            response.put("message", "Student created successfully with ID: " + createdStudent.getStudentId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating student: " + e.getMessage());
            response.put("error", "Error creating student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('TEACHER','PRINCIPAL')")
    public ResponseEntity<Object> getAll() {
        try {
            List<StudentEntity> students = studentService.getAll();
            if (students.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No students found.");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching all students: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error fetching all students: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBy/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','PRINCIPAL',STUDENT)")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            StudentEntity student = studentService.getById(id);
            if (student != null) {
                return new ResponseEntity<>(student, HttpStatus.OK);
            } else {
                response.put("error", "Student with ID " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error fetching student with ID " + id + ": " + e.getMessage());
            response.put("error", "Error fetching student with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','PRINCIPAL')")
    public ResponseEntity<Map<String, String>> update(@RequestBody StudentEntity students, @PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            StudentEntity updatedStudent = studentService.update(students, id);
            response.put("message", "Student with ID " + id + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) { // Assuming NoSuchElementException for not found
            System.err.println("Error updating student with ID " + id + ": " + e.getMessage());
            response.put("error", "Student with ID " + id + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error updating student with ID " + id + ": " + e.getMessage());
            response.put("error", "Error updating student with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','PRINCIPAL')")
    public ResponseEntity<Map<String, String>> delete (@PathVariable int id){
        Map<String,String> response = new HashMap<>();
        try {
            studentService.delete(id);
            response.put("message","Deleted Successfully ID:-"+id);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error deleting student with ID:- " + id + ": " + e.getMessage());
            response.put("error","Error in Deleting this ID:- "+id + ": " + e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}