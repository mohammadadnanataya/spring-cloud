/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adnan.courseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/courses")
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
    
    @GetMapping("/{courseName}")
    public List<Student> findStudentsByCoursename(@RequestParam("courseName") String name) {
        List<Student> students = new ArrayList<>();
        Course course = courses.stream().filter(it -> it.getName().equals(name)).findFirst().get();
        course.getStudentid().forEach((id) -> students.add(studentServiceClient.findById(id)));
        return students;
    }
    
    @GetMapping("/student/{studentName}")
    public List<Course> findCoursenameByStudent(@RequestParam("studentName") String name) {
        List<Student> students = studentServiceClient.findByName(name);
        List<Long> ids = new ArrayList<>();
        students.forEach((student) -> ids.add(student.getId()));
        return courses.stream().filter(it -> it.getStudentid().containsAll(ids)).collect(Collectors.toList());
    }
    
}

@FeignClient("student-service")
interface StudentServiceClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/students/student/{id}")
    Student findById(@PathVariable("id") Long id);
    
    @RequestMapping(method = RequestMethod.GET, value = "/students/{name}")
    List<Student> findByName(@PathVariable("name") String name);
    
}
