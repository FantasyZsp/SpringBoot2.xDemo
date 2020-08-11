package xyz.mydev.common.beans;

/**
 * @author ZSP
 */
@lombok.Data
public class DataDtoImpl implements Data {
  public DataDtoImpl() {
    this.id = "id";
    this.name = "name";
    this.age = "age";
    this.info = "info";
    this.route = "route";
  }

  private String id;
  private String name;
  private String age;
  private String info;
  private String route;

  @Override
  public String getInfo() {
    return route;
  }

  @Override
  public String getRoute() {
    return route;
  }

  @Override
  public void setInfo(String info) {
    this.info = info;
  }

  @Override
  public void setRoute(String route) {
    this.route = route;
  }
}
