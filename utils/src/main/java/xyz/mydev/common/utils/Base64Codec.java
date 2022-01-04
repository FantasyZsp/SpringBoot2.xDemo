package xyz.mydev.common.utils;

import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Base64Codec {
  @Test
  public void decode() throws NoSuchAlgorithmException, NoSuchPaddingException {

    byte[] sessionKey = Base64Utils.decodeFromString("JJmV15Hs+ESybTE3DqWldQ==");
    byte[] encryptedData = Base64Utils.decodeFromString("ZTgtBaBn/jlLmbSVSnPTiXrRYasYXRgUBX+mwHXi8STlzGZz6rEb3reNNUiTRTukmW/XDC/qfpVrMQV6s+w7HCyb2sodXU922W2FmuuCY8/Fy4X1njr59jwR6kVEYayEWNVZTvA+IpuKjqB0UAGO4YIRjkHvpoTOnXgrX78+sZEuuNf3Xo9QnbdZnkcs9hTyKc0sAKCTIQ2Ytkq+n+Op3Q==");
    byte[] iv = Base64Utils.decodeFromString("eias/sch7DuvULgtolWdNg==");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");


  }
}
