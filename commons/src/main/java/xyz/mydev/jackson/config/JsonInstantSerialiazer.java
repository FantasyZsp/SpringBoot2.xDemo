package xyz.mydev.jackson.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

/**
 * @author zhao 2018/09/08 11:22
 */
public class JsonInstantSerialiazer extends JsonSerializer<Instant> {
  @Override
  public void serialize(Instant instant, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
    if (instant == null) {
      gen.writeNull();
    } else {
      gen.writeNumber(instant.toEpochMilli());
    }
  }
}
