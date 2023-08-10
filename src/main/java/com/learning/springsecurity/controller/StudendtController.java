package com.learning.springsecurity.controller;

import com.learning.springsecurity.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudendtController {
    private List<Student> Students = Arrays.asList(
            new Student(1,"Manohar"),
            new Student(2,"Anirudh"),
            new Student(3,"Yeswanth")
    ) ;
    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return Students.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student does not found with Id " + String.valueOf(studentId)));
    }
}
