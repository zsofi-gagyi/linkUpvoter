package com.notReddit.exercise.controller;

import com.notReddit.exercise.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

  MainService mainService;

  public SignInController(MainService mainService) {
    this.mainService = mainService;
  }

  @PutMapping("/signInOrUp")
  public String signInOrUp(@RequestParam(name = "name") String userName, @RequestParam String password) {

    if (this.mainService.userExists(userName)) {
      if (this.mainService.passwordIsGood(userName, password)) {
        return "redirect:/forum/" + this.mainService.getUserId(userName) + "/1/page";
      }
      return "redirect:/forum/";
    }

    this.mainService.createNewUser(userName, password);
    return "redirect:/forum/" + this.mainService.getUserId(userName) + "/1/page";
  }
}


