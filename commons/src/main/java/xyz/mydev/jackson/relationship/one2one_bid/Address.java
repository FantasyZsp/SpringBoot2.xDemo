package xyz.mydev.jackson.relationship.one2one_bid;

import javax.persistence.Embeddable;

/**
 * @author zhao
 * @date 2018/07/24:09/27
 * @description
 */
@Embeddable
public class Address {
  private String address;
  private String postCode;

  public Address() {
  }

  public Address(String address, String postCode) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Address that = (Address) o;

    if (!address.equals(that.address)) return false;
    return postCode.equals(that.postCode);
  }

  @Override
  public int hashCode() {
    int result = address.hashCode();
    result = 31 * result + postCode.hashCode();
    return result;
  }
}
