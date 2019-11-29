package xyz.mydev.beans.dto;

/**
 * @author  zhao
 * @date  2018/07/24:09/27
 * @description
 */
public class AddressDTO {
  private String address;
  private String postCode;

  public AddressDTO() {
  }

  public AddressDTO(String address, String postCode) {
    this.address = address;
    this.postCode = postCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AddressDTO addressDTO1 = (AddressDTO) o;

    if (!address.equals(addressDTO1.address)) return false;
    return postCode.equals(addressDTO1.postCode);
  }

  @Override
  public int hashCode() {
    int result = address.hashCode();
    result = 31 * result + postCode.hashCode();
    return result;
  }
}
