package com.notReddit.testUtilities;

import java.time.LocalDate;
import java.util.List;

public class TimeTranslatorTestData {
  public LocalDate date;
  public String string;

  public TimeTranslatorTestData(LocalDate date, String string){
    this.date = date;
    this.string = string;
  }

  public static void setUpOneElementTranslations(List<TimeTranslatorTestData> _testDataList){
    LocalDate today = LocalDate.now();
    _testDataList.add(new TimeTranslatorTestData(today, "today"));

    LocalDate yesterday = LocalDate.now().minusDays(1);
    _testDataList.add(new TimeTranslatorTestData(yesterday, "yesterday"));

    LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
    _testDataList.add(new TimeTranslatorTestData(twoDaysAgo, "2 days ago"));

    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
    _testDataList.add(new TimeTranslatorTestData(oneMonthAgo, "a month ago"));

    LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
    _testDataList.add(new TimeTranslatorTestData(twoMonthsAgo, "2 months ago"));

    LocalDate oneYearAgo = LocalDate.now().minusYears(1);
    _testDataList.add(new TimeTranslatorTestData(oneYearAgo, "a year ago"));

    LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
    _testDataList.add(new TimeTranslatorTestData(twoYearsAgo, "2 years ago"));
  }

  public static void setUpTwoElementTranslations(List<TimeTranslatorTestData> testDataList){
    LocalDate twoMonthsAndOneDayAgo = LocalDate.now().minusMonths(2).minusDays(1);
    testDataList.add(new TimeTranslatorTestData(twoMonthsAndOneDayAgo, "2 months and a day ago"));

    LocalDate oneYearAndThreeMonthsAgo = LocalDate.now().minusYears(1).minusMonths(3);
    testDataList.add(new TimeTranslatorTestData(oneYearAndThreeMonthsAgo, "a year and 3 months ago"));

    LocalDate twoYearsAndThreeDaysAgo = LocalDate.now().minusYears(2).minusDays(3);
    testDataList.add(new TimeTranslatorTestData(twoYearsAndThreeDaysAgo, "2 years and 3 days ago"));
  }

  public static void setUpThreeElementTranslations(List<TimeTranslatorTestData> _testDataList){
    LocalDate oneYearTwoMonthsAndOneDayAgo = LocalDate.now().minusYears(1).minusMonths(2).minusDays(1);
    _testDataList.add(new TimeTranslatorTestData(oneYearTwoMonthsAndOneDayAgo, "a year, 2 months and a day ago"));

    LocalDate twoYearsOneMonthAndThreeDaysAfo = LocalDate.now().minusYears(2).minusMonths(1).minusDays(3);
    _testDataList.add(new TimeTranslatorTestData(twoYearsOneMonthAndThreeDaysAfo, "2 years, a month and 3 days ago"));
  }
}