package xyz.mydev.jackson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.jackson.relationship.m2m.Teacher;

/**
 * @author zhao
 * @date 2018/09/08 15:20
 * @description
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
