package xyz.mydev.datastructure.serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP  2018/11/20 16:03
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class JobQueueVO implements Serializable {
  private Integer jobQueueId;
  private String jobQueueCode;
  private String craneCode;
  private String craneName;
  private String jobType;
  private String userName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String truckNo;
  private String ocrTruckNo;
  private Integer totalWeight;
  private int containerAmount;
  private List<Container> containers = new ArrayList<>(2);
  private List<String> pictures = new ArrayList<>();
  private Process processes = new Process();

  public JobQueueVO() {
  }

  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Getter
  @Setter
  public static class Process implements Serializable {
    private CommonStep uploadPicture;
    private CommonStep createQueue;
    private CommonStep updateContainer;
    private CommonStep submitToTos;
    private TosStep replyFromTos;
    private TosStep updateVesselPosition;

    public Process() {
      this.uploadPicture = new CommonStep(1, false, "图片上传", LocalDateTime.now());
      this.createQueue = new CommonStep(2, false, "提交队列", LocalDateTime.now());
      this.updateContainer = new CommonStep(3, false, "更新集装箱", LocalDateTime.now());
      this.submitToTos = new CommonStep(4, false, "提交TOS", LocalDateTime.now());
      this.replyFromTos = new TosStep(5, "TOS反馈", LocalDateTime.now());
      this.updateVesselPosition = new TosStep(6, "修改bay位", LocalDateTime.now());
    }
  }

  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Getter
  @Setter
  public static class CommonStep implements Serializable {
    private int step;
    private boolean status;
    private String display;
    private LocalDateTime updateTime;

    public CommonStep() {
    }

    public CommonStep(int step, boolean status, String display, LocalDateTime updateTime) {
      this.step = step;
      this.status = status;
      this.display = display;
      this.updateTime = updateTime;
    }
  }

  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Getter
  @Setter
  public static class TosStep implements Serializable {
    private int step;
    private String display;
    private LocalDateTime updateTime;
    List<ReturnMsg> status = new ArrayList<>(2);

    public TosStep() {
    }

    public TosStep(int step, String display, LocalDateTime updateTime) {
      this.step = step;
      this.display = display;
      this.updateTime = updateTime;
      status.add(new ReturnMsg(false, "", ""));
      status.add(new ReturnMsg(false, "", ""));
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Getter
    @Setter
    public static class ReturnMsg implements Serializable {
      private boolean status;
      private String returnMessage;
      private String containerNumber;

      public ReturnMsg() {
      }

      public ReturnMsg(boolean status, String returnMessage, String containerNumber) {
        this.status = status;
        this.returnMessage = returnMessage;
        this.containerNumber = containerNumber;
      }
    }
  }

  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  @Getter
  @Setter
  public static class Container implements Serializable {

    private String position;
    private String containerNo;
    private String ocrContainerNo;
    private String isoCode;
    private String jobType;
    private String vesselPosition;
    private String yardPosition;
    private String containerProperty;

    @JsonProperty("is_matched")
    private boolean matched;

    private List<Damage> damages = new ArrayList<>(2);

    public Container() {
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Getter
    @Setter
    public static class Damage implements Serializable {
      private String side;
      private String damagePart;
      private String damageGrade;
      private String remark;
      private List<String> damageCodes = new ArrayList<>();
    }
  }
}
