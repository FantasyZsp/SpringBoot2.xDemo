package xyz.mydev.mq.delay;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.mq.delay.repository.MqMessageDelay;
import xyz.mydev.mq.delay.repository.MqMessageDelayDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ZSP
 */
@Service
@Slf4j
public class SimpleDelayService {
  private final MqMessageDelayDao mqMessageDelayDao;

  public SimpleDelayService(MqMessageDelayDao mqMessageDelayDao) {
    this.mqMessageDelayDao = mqMessageDelayDao;
  }

  public boolean save(MqMessageDelay entity) {
    int insert = mqMessageDelayDao.insert(entity);
    return insert > 0;
  }

  public MqMessageDelay findOne(String id) {
    return mqMessageDelayDao.selectById(id);
  }

  public boolean existById(String id) {
    if (StringUtils.isBlank(id)) {
      return false;
    }

    return mqMessageDelayDao.existById(id);
  }

  public boolean updateStatusWhenConsumeError(String id, int status, String mark) {
    return mqMessageDelayDao.updateStatusWhenConsumeError(id, status, mark) == 1;
  }


  public boolean updateToSent(String id) {
    return updateToSent(id, null);
  }

  public boolean updateToSent(String id, String mark) {
    int affectCount = mqMessageDelayDao.updateToSent(id, mark);
    return affectCount == 1;
  }


  public boolean updateStatus(String id, int status) {
    return updateStatus(id, status, null);
  }

  public boolean updateStatus(String id, int status, String mark) {
    int affectCount = mqMessageDelayDao.updateStatus(id, status, mark);
    return affectCount == 1;
  }

  /**
   * 闭区间，缓存去重
   */
  public List<MqMessageDelay> findWillSentBetween(LocalDateTime startTime, LocalDateTime endTime, int limitSize) {
    return mqMessageDelayDao.findWillSentBetween(startTime, endTime, limitSize);
  }

  /**
   * 闭区间，缓存去重
   */
  public List<MqMessageDelay> findWillSent(LocalDateTime startTime, LocalDateTime endTime) {
    return mqMessageDelayDao.findWillSent(startTime, endTime);
  }

  /**
   * 闭区间，缓存去重
   */
  public long countWillSentBetween(LocalDateTime startTime, LocalDateTime endTime) {
    return mqMessageDelayDao.countWillSentBetween(startTime, endTime);
  }

  /**
   * 闭区间，缓存去重
   */
  @Transactional(readOnly = true)
  public List<MqMessageDelay> loadByTime(LocalDateTime startTime, LocalDateTime endTime) {
    final int loadSize = 5000;
    long countWillSent = countWillSentBetween(startTime, endTime);
    log.info("[{}] elements to load", countWillSent);

    if (countWillSent == 0L) {
      return Lists.newArrayList();
    }

    List<MqMessageDelay> willSendList;
    if (countWillSent <= loadSize) {
      willSendList = findWillSentBetween(startTime, endTime, loadSize);
    } else {
      log.warn("load too many data once. size: {}", countWillSent);
      // BAD WAY
      willSendList = findWillSent(startTime, endTime);
    }
    return willSendList;
  }

  public Optional<LocalDateTime> findCheckpoint() {
    return mqMessageDelayDao.findCheckpoint();
  }

  /**
   * 查找下个检查点，如果没有，那么就使用当前检查点
   *
   * @param oldCheckPoint 旧的检查点
   * @return 新的检查点
   */
  public LocalDateTime findNextCheckpointAfter(LocalDateTime oldCheckPoint) {
    Objects.requireNonNull(oldCheckPoint);
    Optional<LocalDateTime> nextCheckpoint = mqMessageDelayDao.findNextCheckpointAfter(oldCheckPoint);
    return nextCheckpoint.orElse(oldCheckPoint);
  }

}
