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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    
    @Autowired
    private StudentServiceClient studentServiceClient;
    
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
        course.getStudentid().forEach((id) -> students.add(studentServiceClient.findById(id)));
        return students;
    }
    
    @GetMapping("/{student-name}")
    public Stream findCoursenameByStudent(@RequestParam("student-name") String name) {
        Student student = studentServiceClient.findByName(name);
        return courses.stream().filter(it -> it.getStudentid().contains(student.getId()));
    }
    
}

@FeignClient("student-service")
interface StudentServiceClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/student/{id}")
    Student findById(@RequestParam("id") Long id);
    
    @RequestMapping(method = RequestMethod.GET, value = "/student/{name}")
    Student findByName(@RequestParam("name") String name);
    
}
