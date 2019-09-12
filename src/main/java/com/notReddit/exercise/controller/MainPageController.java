package com.notReddit.exercise.controller;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.representation.PostRep;
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

  @GetMapping(value = { "/forum",
    "/forum/{pageNumber}/page",
    "/forum/{userId}/{pageNumber}/page",
    "/forum/page/{postsPerPage}",
    "/forum/{userId}/page/{postsPerPage}",
    "/forum/{postsPerPage}"})
  public String getPage(Model model,
    @PathVariable(required = false) Long userId,
    @PathVariable(required = false) Integer pageNumber,
    @PathVariable(required = false) Integer postsPerPage) {

    userId = (userId == null) ? 0 : userId;
    pageNumber = (pageNumber == null) ? 1 : pageNumber;
    postsPerPage = (postsPerPage == null) ?  7 : postsPerPage;

    List<Post> rawResults = this.mainService.createPage(pageNumber, postsPerPage);
    List<PostRep> allPosts = this.mainService.translatePosts(rawResults, userId);
    List<Integer> pageLinks = this.mainService.getPageLinks(postsPerPage);

    model.addAttribute("allPosts", allPosts);
    model.addAttribute("userId", userId);
    model.addAttribute("pageLinks",  pageLinks);
    model.addAttribute("pageNumber", pageNumber);

    return "main";
  }

  @GetMapping("/")
  public String redirectToDefault() {
    return "redirect:/forum";
  }
}
