package xyz.mydev.jackson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mydev.jackson.relationship.m2m.Student;
import xyz.mydev.jackson.relationship.m2m.Teacher;
import xyz.mydev.jackson.relationship.many2one_un.ManyPlan;
import xyz.mydev.jackson.relationship.many2one_un.OneUser;
import xyz.mydev.jackson.relationship.one2one_bid.Address;
import xyz.mydev.jackson.relationship.one2one_bid.Plan;
import xyz.mydev.jackson.relationship.one2one_bid.User;
import xyz.mydev.jackson.repository.MPlanRepository;
import xyz.mydev.jackson.repository.OUserRepository;
import xyz.mydev.jackson.repository.PlanRepository;
import xyz.mydev.jackson.repository.StudentRepository;
import xyz.mydev.jackson.repository.TeacherRepository;
import xyz.mydev.jackson.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author  zhao
 * @date  2018/09/11 20:23
 * @description
 */
@Service
public class RecordService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OUserRepository oUserRepository;

  @Autowired
  private MPlanRepository mPlanRepository;

  @Autowired
  private PlanRepository planRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private TeacherRepository teacherRepository;

  public Object createRecords4m2o(Integer i) {
    if (i == null || i > 9999) {
      i = 1000;
    }
    List<OneUser> users = new ArrayList<>(i);
    List<ManyPlan> plans = new ArrayList<>(i);


    while (i > 0) {
      OneUser user = new OneUser();
      ManyPlan plan = new ManyPlan();
      ManyPlan plan2 = new ManyPlan();

      String uuid = UUID.randomUUID().toString();
      String uuid2 = UUID.randomUUID().toString();

      user.setId(null);
      user.setUsername("username" + i);
      users.add(user);

      plan.setId(null);
      plan.setInstant(Instant.now());
      plan.setCode(uuid);
      plan.setOneUser(user);

      plan2.setId(null);
      plan2.setInstant(Instant.now().plusSeconds(3600));
      plan2.setCode(uuid2);
      plan2.setOneUser(user);

      plans.add(plan);
      plans.add(plan2);
      i--;
    }

    oUserRepository.saveAll(users);
    return mPlanRepository.saveAll(plans);
  }

  public Object createRecords4o2o(Integer i) {
    if (i == null || i > 9999) {
      i = 1000;
    }
    List<User> users = new ArrayList<>(i);
    List<Plan> plans = new ArrayList<>(i);
    while (i > 0) {
      String uuid = UUID.randomUUID().toString();
      System.out.println(uuid);
      LocalDateTime localDateTime = LocalDateTime.now();

      Address address = new Address("address", "postCode");
      User user = new User();
      user.setBirthday(localDateTime);
      user.setPassword("password");
      user.setUsername("username");
      user.setAddress(address);
      users.add(user);

      Plan plan = new Plan();
      plan.setDate(new Date());
      plan.setInstant(Instant.now());
      plan.setInstant2(Instant.now().plusSeconds(3600));
      plan.setMark("mark");
      plan.setNotes("notes");
      plan.setCode(uuid);
      plan.setUser(user);
      plan.setLongTime((long) Math.random() * 10000);
      plans.add(plan);

      i--;
    }

    userRepository.saveAll(users);
    return planRepository.saveAll(plans);
  }

  public Object createTeachersAndStudents(Integer count) {
    if (count == null || count > 9999) {
      count = 1000;
    }
    List<Student> students = new ArrayList<>(count * 2);
    List<Teacher> teachers = new ArrayList<>(count * 2);

    while (count > 0) {
      Teacher teacher = new Teacher();
      teacher.setAge(28 + (int) (Math.random() * 10));
      teacher.setName("tea" + count);

      Teacher teacher2 = new Teacher();
      teacher2.setAge(25 + (int) (Math.random() * 10));
      teacher2.setName("tea2" + count);

      Student stu = new Student();
      stu.setStuAge(18 + count % 10);
      stu.setStuName("name" + count);
      stu.getTeachers().add(teacher);
      stu.getTeachers().add(teacher2);

      Student stu2 = new Student();
      stu2.setStuAge(18 + (int) (Math.random() * 10));
      stu2.setStuName("name2" + count);
      stu2.getTeachers().add(teacher);
      stu2.getTeachers().add(teacher2);

      students.add(stu);
      students.add(stu2);

      teachers.add(teacher);
      teachers.add(teacher2);

      count--;
    }

    teacherRepository.saveAll(teachers);
    return studentRepository.saveAll(students);
  }
}
