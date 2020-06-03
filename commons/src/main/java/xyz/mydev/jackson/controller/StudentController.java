package xyz.mydev.jackson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.jackson.relationship.m2m.Student;
import xyz.mydev.jackson.repository.StudentRepository;

/**
 * @author zhao
 * @date 2018/09/12 15:16
 * @description
 */
@RestController
@RequestMapping("/student")
public class StudentController {

  @Autowired
  private StudentRepository studentRepository;


  //    @PostMapping("/test/save")
  public Object testSave(@RequestBody Student student) {
    System.out.println(student);
    studentRepository.save(student);

    System.out.println(student);

    return student;
  }
}
