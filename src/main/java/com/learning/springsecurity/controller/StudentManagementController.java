package com.learning.springsecurity.controller;

import com.learning.springsecurity.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

    private List<Student> Students = Arrays.asList(
            new Student(1,"Manohar"),
            new Student(2,"Anirudh"),
            new Student(3,"Yeswanth")
    ) ;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getStudents() {
        return Students;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void createStudent(@RequestBody Student student){
        System.out.println("New Student Id is %s " + student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("Delete Student Id is %s " + studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody String studentName){
        System.out.println("Update Stduent with id is " + studentId + studentName);
    }
}
