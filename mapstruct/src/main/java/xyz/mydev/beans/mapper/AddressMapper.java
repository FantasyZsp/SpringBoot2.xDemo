package xyz.mydev.beans.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import xyz.mydev.beans.dto.AddressDTO;

import java.io.IOException;

/**
 * @Auther: zhao
 * @date  2018/07/24:17/23
 * @description
 */
@Component
public class AddressMapper {

  ObjectMapper objectMapper = new ObjectMapper();

  public String toString(AddressDTO addressDTO) {
    try {
      return addressDTO != null ? objectMapper.writeValueAsString(addressDTO) : null;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public AddressDTO toAddressDTO(String address) {
    try {
      return address != null ? objectMapper.readValue(address, AddressDTO.class) : null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
