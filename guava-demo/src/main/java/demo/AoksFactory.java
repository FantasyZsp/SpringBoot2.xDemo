package demo;

import lombok.Data;

@Data
public class AoksFactory implements Factory {

  private String name;

  public AoksFactory(String name) {
    this.name = name;

  }

  @Override
  public Production produce() {
    Production production = new Production();
    production.setName("奥克斯");
    production.setDescription("生产奥克斯空调");
    return production;
  }
}
