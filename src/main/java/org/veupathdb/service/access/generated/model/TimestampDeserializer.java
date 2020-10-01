package org.veupathdb.service.access.generated.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;

public class TimestampDeserializer extends StdDeserializer<Date> {
  private static final StdDateFormat DATE_PARSER = new StdDateFormat();

  public TimestampDeserializer() {
    super(Date.class);}

  public Date deserialize(JsonParser jsonParser, DeserializationContext jsonContext) throws
      IOException, JsonProcessingException {
    try {
      ObjectMapper mapper  = new ObjectMapper();
      String dateString = mapper.readValue(jsonParser, String.class);
      Date date = DATE_PARSER.parse(dateString);
      return date;
      } catch (ParseException e) {throw new IOException(e);
    }
  }
}
