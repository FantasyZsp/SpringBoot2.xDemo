package xyz.mydev.common.beans.dto;

import java.io.Serializable;
import java.time.Instant;


public class PersonDTO implements Serializable {
  private Long id;
  private String namee;
  private AddressDTO address;

  private Instant birthday;

  public PersonDTO() {
  }

  public PersonDTO(Long id, String name, AddressDTO address, Instant birthday) {
    this.id = id;
    this.namee = name;
    this.address = address;
    this.birthday = birthday;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNamee() {
    return namee;
  }

  public void setNamee(String namee) {
    this.namee = namee;
  }

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  public Instant getBirthday() {
    return birthday;
  }

  public void setBirthday(Instant birthday) {
    this.birthday = birthday;
  }


  @Override
  public String toString() {
    return "net.PersonDTO{" +
      "id=" + id +
      ", namee='" + namee + '\'' +
      ", address=" + address +
      ", birthday=" + birthday +
      '}';
  }
}
