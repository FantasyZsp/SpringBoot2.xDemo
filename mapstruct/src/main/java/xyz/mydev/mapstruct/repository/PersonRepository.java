package xyz.mydev.mapstruct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.mapstruct.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
