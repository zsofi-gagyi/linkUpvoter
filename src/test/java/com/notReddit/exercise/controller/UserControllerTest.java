package com.notReddit.exercise.controller;

import com.notReddit.exercise.service.MainService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @MockBean
  static MainService mainService;

  @BeforeClass
  public static void setUp(){
    //set up mainService in an optimistic way?
  }

  @Test
  public void signInOrUp_correctData_signs_In() {

  }

  @Test
  public void signInOrUp_correctData_signs_Up() {

  }

  @Test
  public void signInOrUp_badPassword_400_withMessage() {

  }

  @Test
  public void signInOrUp_errorCreatingUser_500() {

  }
}