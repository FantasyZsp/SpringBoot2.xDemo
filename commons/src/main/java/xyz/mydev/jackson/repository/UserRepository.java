package xyz.mydev.jackson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.mydev.jackson.relationship.one2one_bid.User;

/**
 * @author  zhao
 * @date  2018/09/08 15:20
 * @description
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
