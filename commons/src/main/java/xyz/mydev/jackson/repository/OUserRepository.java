package xyz.mydev.jackson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.jackson.relationship.many2one_un.OneUser;

/**
 * @author zhao
 * @date 2018/09/08 15:20
 * @description
 */
public interface OUserRepository extends JpaRepository<OneUser, Long> {

}
