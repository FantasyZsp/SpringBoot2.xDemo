package xyz.mydev.mq.delay.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * @author ZSP
 */
@Mapper
@Repository
public interface MqMessageDelayDao {
  MqMessageDelay selectById(@Param("id") String id);

  String selectIdById(@Param("id") String id);

  int insert(MqMessageDelay entity);

  int updateToSent(@Param("id") String id, @Param("mark") String mark);

  List<MqMessageDelay> findWillSentBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("limitSize") int limitSize);

  long countWillSentBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

  List<MqMessageDelay> findWillSent(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

  int updateStatus(@Param("id") String id, @Param("status") int status, @Param("mark") String mark);

  int updateStatusWhenConsumeError(@Param("id") String id, @Param("status") int status, @Param("mark") String mark);


  Optional<LocalDateTime> findCheckpoint();

  Optional<LocalDateTime> findNextCheckpointAfter(@Param("oldCheckPoint") LocalDateTime oldCheckPoint);

  boolean existById(@Param("id") String id);
}