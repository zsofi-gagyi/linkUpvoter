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
      String ending = (period.getYears() == 1)? " year" : " years";
      timeDescriptions.add(period.getYears() + ending);
    }

    if (period.getMonths() > 0) {
      String ending = (period.getMonths() == 1)? " month" : " months";
      timeDescriptions.add(period.getMonths() + ending);
    }

    if (period.getDays() > 0) {
      String ending = (period.getDays() == 1)? " day" : " days";
      timeDescriptions.add(period.getDays() + ending);
    }

    switch (timeDescriptions.size()) {
      case 0:
        return "today";
      case 1:
        return timeDescriptions.get(0) + " ago";
      case 2:
        return timeDescriptions.get(0) + " and " + timeDescriptions.get(1) + " ago";
      default:
        return timeDescriptions.get(0) + ", " + timeDescriptions.get(1) + " and " + timeDescriptions.get(2) + " ago";
    }
  }
}
