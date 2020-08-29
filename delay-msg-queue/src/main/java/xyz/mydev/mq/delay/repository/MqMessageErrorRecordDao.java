package xyz.mydev.mq.delay.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ZSP
 */
@Repository
@Mapper
public interface MqMessageErrorRecordDao {
  int deleteByPrimaryKey(String id);

  int insert(MqMessageErrorRecord record);


}