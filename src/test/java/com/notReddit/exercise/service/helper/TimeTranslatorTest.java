package com.notReddit.exercise.service.helper;

import com.notReddit.testUtilities.TestData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TimeTranslatorTest {

  private static ArrayList<TestData> _testData;
  private static LocalDate _futureDate;
  private static TimeTranslator _timeTranslator;

  @BeforeClass
  public static void setUp(){
    _timeTranslator = new TimeTranslator();
    _testData = new ArrayList<>();
    _futureDate = LocalDate.now().minusDays(-1);

    setUpOneElementTranslations();
    setUpTwoElementTranslations();
    setUpThreeElementTranslations();
  }

  @Test
  public void describesCorrectPeriod_correctly() {
      for (TestData testData : _testData) {
        String result = _timeTranslator.describePeriodSince(testData.date);
        assertEquals(result, testData.string);
      }
  }

  @Test(expected = ArithmeticException.class)
  public void periodInTheFuture_exceptionType() {
      _timeTranslator.describePeriodSince(_futureDate);
  }

  @Test
  public void periodInTheFuture_exceptionMessage() {
    String receivedErrorMessage = "";
    String expectedErrorMessage = "The date seems to be from the future";

    try {
      _timeTranslator.describePeriodSince(_futureDate);
    } catch (Exception e) {
      receivedErrorMessage = e.getMessage();
    }

    assertEquals(expectedErrorMessage, receivedErrorMessage);
  }

  private static void setUpOneElementTranslations(){
    LocalDate today = LocalDate.now();
    _testData.add(new TestData(today, "today"));

    LocalDate oneDayAgo = LocalDate.now().minusDays(1);
    _testData.add(new TestData(oneDayAgo, "1 day ago"));

    LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
    _testData.add(new TestData(twoDaysAgo, "2 days ago"));

    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
    _testData.add(new TestData(oneMonthAgo, "1 month ago"));

    LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
    _testData.add(new TestData(twoMonthsAgo, "2 months ago"));

    LocalDate oneYearAgo = LocalDate.now().minusYears(1);
    _testData.add(new TestData(oneYearAgo, "1 year ago"));

    LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
    _testData.add(new TestData(twoYearsAgo, "2 years ago"));
  }

  private static void setUpTwoElementTranslations(){
    LocalDate twoMonthsAndOneDayAgo = LocalDate.now().minusMonths(2).minusDays(1);
    _testData.add(new TestData(twoMonthsAndOneDayAgo, "2 months and 1 day ago"));

    LocalDate oneYearAndThreeMonthsAgo = LocalDate.now().minusYears(1).minusMonths(3);
    _testData.add(new TestData(oneYearAndThreeMonthsAgo, "1 year and 3 months ago"));

    LocalDate twoYearsAndThreeDaysAgo = LocalDate.now().minusYears(2).minusDays(3);
    _testData.add(new TestData(twoYearsAndThreeDaysAgo, "2 years and 3 days ago"));
  }

  private static void setUpThreeElementTranslations(){
    LocalDate oneYearTwoMonthsAndOneDayAgo = LocalDate.now().minusYears(1).minusMonths(2).minusDays(1);
    _testData.add(new TestData(oneYearTwoMonthsAndOneDayAgo, "1 year, 2 months and 1 day ago"));

    LocalDate twoYearsOneMonthAndThreeDaysAfo = LocalDate.now().minusYears(2).minusMonths(1).minusDays(3);
    _testData.add(new TestData(twoYearsOneMonthAndThreeDaysAfo, "2 years, 1 month and 3 days ago"));
  }
}