package xyz.mydev.jackson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.mydev.jackson.relationship.many2one_un.ManyPlan;

/**
 * @author  zhao
 * @date  2018/09/08 15:20
 * @description
 */

public interface MPlanRepository extends JpaRepository<ManyPlan, Long> {

  @Query(value = "SELECT * FROM many_plan p WHERE p.id = ?1 ", nativeQuery = true)
  ManyPlan findOne(Long id);

  @Query(value = "SELECT p.code FROM many_plan p WHERE p.id = ?1 ", nativeQuery = true)
  String findCodeById(Long id);

  @Query(value = "SELECT p.user_id FROM many_plan p WHERE p.id = ?1 ", nativeQuery = true)
  Long findUserIdById(Long id);

}
