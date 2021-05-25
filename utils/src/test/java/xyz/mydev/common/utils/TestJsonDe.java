package xyz.mydev.common.utils;

import lombok.ToString;
import org.junit.Test;

/**
 * @author ZSP
 */
public class TestJsonDe {

  @Test
  public void testJsonDe() {
    String json = "{\"cardNumber\": \"XXXXX\", \"bankDeposit\": \"中国建设银行\"}";


    Bank bank = JsonUtil.string2Obj(json, Bank.class);

    System.out.println(bank);
    System.out.println("xx");
    System.out.println(JsonUtil.obj2String(bank));

  }


  @ToString
  static class Bank {
    private String cardNumber222;
    private String BankDeposit;

    public String getCardNumber() {
      return cardNumber222;
    }

    public void setCardNumber(String cardNumber) {
      this.cardNumber222 = cardNumber;
    }

    public String getBankDeposit() {
      return BankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
      this.BankDeposit = bankDeposit;
    }
  }
}
