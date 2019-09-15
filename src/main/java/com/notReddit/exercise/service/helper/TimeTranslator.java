package com.notReddit.exercise.service.helper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimeTranslator {

  public String describePeriodSince(LocalDate creationDate) {
    LocalDate now = LocalDate.now();
    Period period = Period.between(creationDate, now);

    if (period.isNegative()){
      throw new ArithmeticException("The date seems to be from the future");
    }

    List<String> timeDescriptions = new ArrayList<>();

    if (period.getYears() > 0) {
      if (period.getYears() == 1){
        timeDescriptions.add("a year");
      } else {
        timeDescriptions.add(period.getYears() + " years");
      }
    }

    if (period.getMonths() > 0) {
      if (period.getMonths() == 1){
        timeDescriptions.add("a month");
      } else {
        timeDescriptions.add(period.getMonths() + " months");
      }
    }

    if (period.getDays() > 0) {
      if (period.getDays() == 1){
        timeDescriptions.add("a day");
      } else {
        timeDescriptions.add(period.getDays() + " days");
      }
    }

    switch (timeDescriptions.size()) {
      case 0:
        return "today";
      case 1:
        String timeDescription = timeDescriptions.get(0);
        if (timeDescription.equals("a day")){
          return "yesterday";
        }
        return timeDescriptions.get(0) + " ago";
      case 2:
        return timeDescriptions.get(0) + " and " + timeDescriptions.get(1) + " ago";
      default:
        return timeDescriptions.get(0) + ", " + timeDescriptions.get(1) + " and " + timeDescriptions.get(2) + " ago";
    }
  }
}
