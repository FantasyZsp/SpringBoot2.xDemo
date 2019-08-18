package xyz.mydev.jackson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.jackson.relationship.m2m.Teacher;
import xyz.mydev.jackson.repository.TeacherRepository;

/**
 * @author  zhao
 * @date  2018/09/12 15:16
 * @description
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

  @Autowired
  private TeacherRepository teacherRepository;


  @PostMapping("/test/save")
  @Transactional
  public Object testSave(@RequestBody Teacher teacher) {

    System.out.println(teacher);

    teacherRepository.save(teacher);
    System.out.println(teacher);

    return teacher;
  }
}
