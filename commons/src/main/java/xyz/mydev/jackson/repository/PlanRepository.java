package xyz.mydev.jackson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.mydev.jackson.relationship.one2one_bid.Plan;

/**
 * @author  zhao
 * @date  2018/09/08 15:20
 * @description
 */

public interface PlanRepository extends JpaRepository<Plan, Long> {

  @Query(value = "SELECT * FROM plan p WHERE p.id = ?1 ", nativeQuery = true)
  Plan findOneById(Long id);

  @Query(value = "SELECT p.code FROM plan p WHERE p.id = ?1 ", nativeQuery = true)
  String findCodeById(Long id);

  @Query(value = "SELECT p.user_id FROM plan p WHERE p.id = ?1 ", nativeQuery = true)
  Long findUserIdById(Long id);

}
