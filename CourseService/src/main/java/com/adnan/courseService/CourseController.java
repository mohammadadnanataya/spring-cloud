/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adnan.courseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private List<Course> courses = new ArrayList<>();
    
    @PostMapping
    public Course add(@RequestBody Course c) {
        c.setId((long) (courses.size() + 1));
        courses.add(c);
        return c;
    }
    
    @GetMapping("/{course-name}")
    public List<Student> findStudentsByCoursename(@RequestParam("course-name") String name) {
        List<Student> students = new ArrayList<>();
        Course course = courses.stream().filter(it -> it.getName().equals(name)).findFirst().get();
        course.getStudentid().forEach((id) -> students.add(restTemplate.getForObject("http://student-service/student/{id}?id="+id, Student.class)));
        return students;
    }
    
    @GetMapping("/{student-name}")
    public Stream<Course> findCoursenameByStudent(@RequestParam("student-name") String name) {
        Student student = restTemplate.getForObject("http://student-service/student/{name}?name="+name, Student.class);
        return courses.stream().filter(it -> it.getStudentid().contains(student.getId()));
    }
    
}
