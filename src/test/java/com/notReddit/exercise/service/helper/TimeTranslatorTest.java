package com.notReddit.exercise.service.helper;

import com.notReddit.testUtilities.TimeTranslatorTestData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TimeTranslatorTest {

  private static ArrayList<TimeTranslatorTestData> _testDataList;
  private static LocalDate _futureDate;
  private static TimeTranslator _timeTranslator;

  @BeforeClass
  public static void setUp(){
    _timeTranslator = new TimeTranslator();
    _testDataList = new ArrayList<>();
    _futureDate = LocalDate.now().minusDays(-1);

    TimeTranslatorTestData.setUpOneElementTranslations(_testDataList);
    TimeTranslatorTestData.setUpTwoElementTranslations(_testDataList);
    TimeTranslatorTestData.setUpThreeElementTranslations(_testDataList);
  }

  @Test
  public void describesCorrectPeriod_correctly() {
      for (TimeTranslatorTestData testData : _testDataList) {
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
}