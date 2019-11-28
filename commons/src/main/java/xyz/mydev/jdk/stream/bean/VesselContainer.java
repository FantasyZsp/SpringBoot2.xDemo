package xyz.mydev.jdk.stream.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP  2018/11/06 19:54
 */
@Getter
@Setter
public class VesselContainer {

  private int id;
  private String vesselNumber;
  private String containerNumber;
  private String blNumber;
  private int weight;
  private String containerStatus;

  public VesselContainer() {
  }

  public VesselContainer(String vesselNumber, String containerNumber, String blNumber, int weight, String containerStatus) {
    this.vesselNumber = vesselNumber;
    this.containerNumber = containerNumber;
    this.blNumber = blNumber;
    this.weight = weight;
    this.containerStatus = containerStatus;
  }

  public VesselContainer(int id, String vesselNumber, String containerNumber, String blNumber, int weight, String containerStatus) {
    this.id = id;
    this.vesselNumber = vesselNumber;
    this.containerNumber = containerNumber;
    this.blNumber = blNumber;
    this.weight = weight;
    this.containerStatus = containerStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VesselContainer that = (VesselContainer) o;

    if (!vesselNumber.equals(that.vesselNumber)) return false;
    return containerNumber.equals(that.containerNumber);
  }

  @Override
  public int hashCode() {
    int result = vesselNumber.hashCode();
    result = 31 * result + containerNumber.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "VesselContainer{" +
      "vesselNumber='" + vesselNumber + '\'' +
      ", containerNumber='" + containerNumber + '\'' +
      ", blNumber='" + blNumber + '\'' +
      ", weight=" + weight +
      ", containerStatus='" + containerStatus + '\'' +
      '}';
  }

  public static List<VesselContainer> generateContainers(int count) {
    if (count <= 0) {
      count = 1;
    }
    String tmpContainerNo = "";
    String vesselVoyageNumber = LocalDate.now().toString();
    List<VesselContainer> list = new ArrayList<>(count);
    for (int j = 0; j < count; j++) {
      String containerNumber = generateContainerNumber();
      String blNumber = String.valueOf(j);
      String containerStatus = String.valueOf(j % 2 == 0);
      if (j % 3 == 0) {
        tmpContainerNo = containerNumber;
      }
      if (j % 3 == 0 || j % 4 == 0 || j % 5 == 0) {
        list.add(new VesselContainer(vesselVoyageNumber, tmpContainerNo, blNumber, j, containerStatus));
      } else {
        list.add(new VesselContainer(vesselVoyageNumber, containerNumber, blNumber, j, containerStatus));
      }
    }

    return list;
  }

  public static String generateContainerNumber() {
    // 生成箱号
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder azLists = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      azLists.append(chars.charAt((int) (Math.random() * 26)));
    }
    int number7 = (int) ((Math.random() * 9 + 1) * 1000000);

    return azLists.toString() + number7;
  }


}
