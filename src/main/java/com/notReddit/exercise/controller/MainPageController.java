package com.notReddit.exercise.controller;

import com.notReddit.exercise.model.representation.PagesView;
import com.notReddit.exercise.model.representation.PostView;
import com.notReddit.exercise.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainPageController {

  MainService mainService;

  public MainPageController(MainService mainService) {
    this.mainService = mainService;
  }

  @GetMapping("/")
  public String redirectToDefault() {
    return "redirect:/users/0/postsPerPage/7/page/1";
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}")
  public String getPage(Model model,
    @PathVariable Long userId,
    @PathVariable Integer postsPerPage,
    @PathVariable Integer pageNumber) {

    PagesView rawResults = this.mainService.createPage(pageNumber, postsPerPage);

    List<PostView> postsToBeDisplayed = this.mainService.translatePosts(rawResults.posts, userId);
    List<Integer> pageLinks = rawResults.pageNumbers;

    model.addAttribute("pageNumber", pageNumber);
    model.addAttribute("userId", userId);
    model.addAttribute("posts", postsToBeDisplayed);
    model.addAttribute("postsPerPage", postsPerPage);
    model.addAttribute("pageLinks",  pageLinks);

    return "main";
  }
}