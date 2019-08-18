package xyz.mydev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.domain.Girl;

public interface GirlRepository extends JpaRepository<Girl, Integer> {
}
