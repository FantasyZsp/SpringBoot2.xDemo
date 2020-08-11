package xyz.mydev.demo;

import lombok.Data;

@Data
public class MeidiFactory implements Factory {

  private String name;

  public MeidiFactory(String name) {
    this.name = name;

  }

  @Override
  public Production produce() {
    Production production = new Production();
    production.setName("美的工厂");
    production.setDescription("生产美的空调");
    return production;
  }
}
