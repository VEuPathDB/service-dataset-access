package org.veupathdb.service.access.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;

public class DateTest {

  @Test
  public void test() {
    DateTimeFormatter dateTime = new DateTimeFormatterBuilder()
        .appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
        .appendPattern(", ")
        .appendValue(ChronoField.DAY_OF_MONTH)
        .appendPattern(" ")
        .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT)
        .appendPattern(" ")
        .appendValue(ChronoField.YEAR)
        .appendPattern(" hh:mm:ss zzz")
        .toFormatter();//Wed, 14 Jun 2017 07:00:00 GMT
    System.out.println(dateTime.format(LocalDateTime.now().atZone(ZoneId.of("GMT"))));
  }
}
