package com.Attendance.Attendance.System.Teachers.Controllers;

import com.Attendance.Attendance.System.Teachers.Entity.TeachersEntity;
import com.Attendance.Attendance.System.Teachers.Service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException; // Import for specific exceptions if needed

@RestController
@RequestMapping("/teachers")
public class TeachersController {

    @Autowired
    private TeachersService teacherService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('PRINCIPAL')")
    public ResponseEntity<Map<String, String>> create(@RequestBody TeachersEntity teachers) {
        Map<String, String> response = new HashMap<>();
        try {
            TeachersEntity createdTeacher = teacherService.create(teachers);
            response.put("message", "Teacher created successfully with ID: " + createdTeacher.getTeacherId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating teacher: " + e.getMessage());
            response.put("error", "Error creating teacher: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('TEACHERS','PRINCIPAL')")
    public ResponseEntity<Object> getAll() {
        try {
            List<TeachersEntity> teachers = teacherService.getAll();
            if (teachers.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No teachers found.");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error fetching all teachers: " + e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error fetching all teachers: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBy/{id}")
    @PreAuthorize("hasAnyRole('TEACHERS','PRINCIPAL')")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            TeachersEntity teacher = teacherService.getById(id);
            if (teacher != null) {
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            } else {
                response.put("error", "Teacher with ID " + id + " not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error fetching teacher with ID " + id + ": " + e.getMessage());
            response.put("error", "Error fetching teacher with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL')")
    public ResponseEntity<Map<String, String>> update(@RequestBody TeachersEntity teachers, @PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        try {
            TeachersEntity updatedTeacher = teacherService.update(teachers, id);
            response.put("message", "Teacher with ID " + id + " updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) { // Assuming NoSuchElementException for not found, adjust if your service throws something else
            System.err.println("Error updating teacher with ID " + id + ": " + e.getMessage());
            response.put("error", "Teacher with ID " + id + " not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.err.println("Error updating teacher with ID " + id + ": " + e.getMessage());
            response.put("error", "Error updating teacher with ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('PRINCIPAL')")
    public ResponseEntity<Map<String, String>> delete (@PathVariable int id){
        Map<String,String> response = new HashMap<>();
        try {
            teacherService.delete(id);
            response.put("message","Deleted Successfully ID:-"+id);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error deleting teacher with ID:- " + id + ": " + e.getMessage());
            response.put("error","Error in Deleting this ID:- "+id + ": " + e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}