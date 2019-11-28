package xyz.mydev.beans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.beans.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
