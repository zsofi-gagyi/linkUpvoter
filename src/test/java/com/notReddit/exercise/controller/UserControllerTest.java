package com.notReddit.exercise.controller;

import com.notReddit.exercise.service.MainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc _mockMvc;

  @MockBean
  MainService _mainService;

  @Test
  public void signInOrUp_existingUser_signs_In() throws Exception {
    Mockito.when(_mainService.userExists("userName"))
      .thenReturn(true);

    Mockito.when(_mainService.passwordIsBad("userName", "userPassword"))
      .thenReturn(false);

    Mockito.when(_mainService.getUserId("userName"))
      .thenReturn(12345L);

    _mockMvc.perform(put("/users/0/postsPerPage/1/page/1/signInOrUp")
                      .param("name", "userName")
                      .param("password", "userPassword"))

      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/users/12345/postsPerPage/1/page/1"));

    verify(_mainService, times(0))
      .createNewUser("userName", "userPassword");
  }

  @Test
  public void signInOrUp_newUserName_signs_Up() throws Exception {
    Mockito.when(_mainService.userExists("userName")).thenReturn(false);
    Mockito.when(_mainService.getUserId("userName")).thenReturn(12345L);

    _mockMvc.perform(put("/users/0/postsPerPage/1/page/1/signInOrUp")
      .param("name", "userName")
      .param("password", "userPassword"))

      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/users/12345/postsPerPage/1/page/1"));

    verify(_mainService, times(1))
      .createNewUser("userName", "userPassword");
  }

  @Test
  public void signInOrUp_badPassword_400_withMessage() throws Exception {
    Mockito.when(_mainService.userExists("userName"))
      .thenReturn(true);

    Mockito.when(_mainService.passwordIsBad("userName", "userPassword"))
      .thenReturn(true);

    _mockMvc.perform(put("/users/0/postsPerPage/1/page/1/signInOrUp")
      .param("name", "userName")
      .param("password", "userPassword"))

      .andExpect(status().isBadRequest())
      .andExpect(status().reason("username and/or password is incorrect"));
  }
}