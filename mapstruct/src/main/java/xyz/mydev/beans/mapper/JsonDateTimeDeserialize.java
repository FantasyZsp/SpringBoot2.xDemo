package xyz.mydev.beans.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author  zhao
 * @date  2018/07/25:21/16
 * @description
 */
public class JsonDateTimeDeserialize extends JsonDeserializer<Instant> {
  @Override
  public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.CHINESE).withZone(ZoneId.systemDefault());

    return null;
  }
}
