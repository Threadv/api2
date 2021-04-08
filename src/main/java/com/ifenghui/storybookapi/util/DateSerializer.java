package com.ifenghui.storybookapi.util;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by jia on 2017/2/15.
 */
public class DateSerializer extends JsonSerializer<Date> {
    @Override
      public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 jgen.writeString(dateFormat.format(value));
  }
}
