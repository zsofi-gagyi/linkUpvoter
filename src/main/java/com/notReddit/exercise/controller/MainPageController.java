package com.notReddit.exercise.controller;

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

  @GetMapping(value = {"/", "/{pageNumber}/page", "/{userId}/{pageNumber}/page"})
  public String getPage(Model model,
    @PathVariable(required = false) Long userId,
    @PathVariable(required = false) Integer pageNumber,
    @PathVariable(required = false) Integer postsPerPage) {

    userId = (userId == null) ? 0 : userId;
    pageNumber = (pageNumber == null) ? 1 : pageNumber;
    postsPerPage = (postsPerPage == null) ?  7 : postsPerPage; //users choosing minimum number of posts / page is
                                                                // a feature to be added

    List<PostRep> allPosts = this.mainService.paginateAndTranslatePosts(pageNumber, postsPerPage, userId);

    model.addAttribute("allPosts", allPosts);
    model.addAttribute("userId", userId);
    model.addAttribute("pageLinks", this.mainService.getPageLinks(postsPerPage));
    model.addAttribute("pageNumber", pageNumber);
    return "main";
  }
}
