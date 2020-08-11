package xyz.mydev.demo;

import lombok.Data;

@Data
public class GeliFactory implements Factory {


  private String name;

  public GeliFactory(String name) {
    this.name = name;

  }

  @Override
  public Production produce() {
    Production production = new Production();
    production.setName("格力工厂");
    production.setDescription("格力空调");
    return production;
  }
}
