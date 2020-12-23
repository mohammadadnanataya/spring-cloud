/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adnan.studentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    
    private List<Student> students = new ArrayList<>();

    @GetMapping("/student/{id}")
    public Student findById(@PathVariable("id") Long id) {
        return students.stream().filter(it -> it.getId().equals(id)).findFirst().get();
    }
    
    @GetMapping("/{name}")
    public List<Student> findByName(@PathVariable("name") String name) {
        return students.stream().filter(it -> it.getName().equals(name)).collect(Collectors.toList());
    }
    
    @PostMapping
    public Student add(@RequestBody Student s) {
        s.setId((long) (students.size() + 1));
        students.add(s);
        return s;
    }
    
}
