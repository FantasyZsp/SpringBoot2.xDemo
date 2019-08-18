package xyz.mydev.mapstruct.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  zhao
 * @date  2018/08/11 15:16
 * @description
 */
public class HashMapDTO {
  private String username;
  private String password;
  private Integer age;
  private Instant time;
  private Map<String, AddressDTO> map = new HashMap<>();

  public HashMapDTO() {
  }

  public HashMapDTO(String username, String password, Integer age, Instant time, Map<String, AddressDTO> map) {
    this.username = username;
    this.password = password;
    this.age = age;
    this.time = time;
    this.map = map;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Instant getTime() {
    return time;
  }

  public void setTime(Instant time) {
    this.time = time;
  }

  public Map<String, AddressDTO> getMap() {
    return map;
  }

  public void setMap(Map<String, AddressDTO> map) {
    this.map = map;
  }
}
