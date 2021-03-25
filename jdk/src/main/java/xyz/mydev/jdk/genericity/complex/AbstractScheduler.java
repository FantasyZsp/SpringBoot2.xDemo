package xyz.mydev.jdk.genericity.complex;

import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.complex.msg.StringMessage;
import xyz.mydev.jdk.genericity.complex.route.PorterRouter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public abstract class AbstractScheduler {
  private final Collection<String> scheduledTables;
  private final PorterRouter porterRouter;
  private final MessageLoader<? extends StringMessage> messageLoader;


  private final ScheduledExecutorService scheduledExecutorService;

  public AbstractScheduler(Collection<String> scheduledTables,
                           PorterRouter porterRouter,
                           MessageLoader<? extends StringMessage> messageLoader) {
    this.scheduledTables = scheduledTables;
    this.porterRouter = porterRouter;
    this.messageLoader = messageLoader;
    this.scheduledExecutorService = Executors.newScheduledThreadPool(scheduledTables.size(), r -> new Thread(r, "Scheduler"));
  }

  public void start() {

    LocalDateTime snapshotTime = LocalDateTime.now();

    for (String tableName : scheduledTables) {

      Runnable startingTask = buildTask(tableName, true);
      scheduledExecutorService.execute(startingTask);

      Runnable scheduleTask = buildTask(tableName, false);
      long period = 30;
      long initialDelay = calculateInitialDelay(snapshotTime, tableName);
      scheduledExecutorService.scheduleAtFixedRate(scheduleTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

  }

  private long calculateInitialDelay(LocalDateTime snapshotTime, String tableName) {
    return 0;
  }

  private ScheduleTask buildTask(String tableName, boolean isStartingTask) {
    return new ScheduleTask(tableName,
      Objects.requireNonNull(porterRouter.get(tableName)),
      messageLoader
    );
  }


}
