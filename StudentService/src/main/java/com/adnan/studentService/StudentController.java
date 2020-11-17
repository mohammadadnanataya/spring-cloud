/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adnan.studentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    
    private List<Student> students = new ArrayList<>();

    @GetMapping("/{id}")
    public Student findById(@RequestParam("id") Long id) {
        return students.stream().filter(it -> it.getId().equals(id)).findFirst().get();
    }
    
    @GetMapping("/{name}")
    public Stream<Student> findByName(@RequestParam("name") String name) {
        return students.stream().filter(it -> it.getName().equals(name));
    }
    
    @PostMapping
    public Student add(@RequestBody Student s) {
        s.setId((long) (students.size() + 1));
        students.add(s);
        return s;
    }
    
}
