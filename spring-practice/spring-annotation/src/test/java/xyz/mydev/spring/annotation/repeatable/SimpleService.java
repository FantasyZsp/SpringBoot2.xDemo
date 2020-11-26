package xyz.mydev.spring.annotation.repeatable;

import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
public class SimpleService {


  @DistributedLock(prefix = "testLockMulti", key = "testLockMulti")
  @DistributedLock(prefix = "testLockMulti222", key = "testLockMulti222")
  public void testLockMulti() {

  }

  @DistributedLock(prefix = "testLockSingle", key = "testLockSingle")
  public void testLockSingle() {

  }

  @DistributedLocks({
    @DistributedLock(prefix = "testLockGroup111", key = "testLockGroup111"),
    @DistributedLock(prefix = "testLockGroup222", key = "testLockGroup222")})
  public void testLockGroup() {

  }

}
