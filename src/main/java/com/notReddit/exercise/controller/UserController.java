package com.notReddit.exercise.controller;

import com.notReddit.exercise.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  MainService mainService;

  public UserController(MainService mainService) {
    this.mainService = mainService;
  }

  @PutMapping("/users/0/postsPerPage/{postsPerPage}/page/{pageNumber}/signInOrUp")
  public String signInOrUp(
    @PathVariable int postsPerPage,
    @PathVariable int pageNumber,
    @RequestParam(name = "name") String userName,
    @RequestParam String password) {

    if (this.mainService.userExists(userName)) {
      if (!this.mainService.passwordIsGood(userName, password)) {
        return "redirect:/users/0/postsPerPage/7/page/1";
      }
    } else {
      this.mainService.createNewUser(userName, password);
    }

    return  "redirect:/users/" + this.mainService.getUserId(userName)  +
            "/postsPerPage/" + postsPerPage + "/page/" + pageNumber;
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}/signOut")
  public String signOut(
    @PathVariable Long userId,
    @PathVariable Integer postsPerPage,
    @PathVariable Integer pageNumber){

    return  "redirect:/users/0/" + "/postsPerPage/" + postsPerPage + "/page/" + pageNumber;
  }
}