package com.ifenghui.storybookapi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jia on 2017/2/15.
 */
public class DateDeserializer  extends JsonDeserializer<Date> {
    @Override
 public Date deserialize(JsonParser jp, DeserializationContext dctx) throws IOException, JsonProcessingException {
 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 try {
 return dateFormat.parse(jp.getValueAsString());
 } catch (ParseException e) {
        e.printStackTrace();
              } finally {
            }
            return null;
       }
}
