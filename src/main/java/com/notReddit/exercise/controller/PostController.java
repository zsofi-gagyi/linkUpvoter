package com.notReddit.exercise.controller;

import com.notReddit.exercise.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

  MainService mainService;

  public PostController(MainService mainService) {
    this.mainService = mainService;
  }

  @GetMapping("/forum/{userId}/submit")
  public String getSubmit(Model model, @PathVariable long userId) {
    model.addAttribute("userId", userId);
    model.addAttribute("user", this.mainService.getUser(userId));
    return "submitPost";
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}/posts/{id}/submit")
  public String getSubmitComment(Model model,

    @PathVariable long userId,
    @PathVariable Integer postsPerPage,
    @PathVariable Integer pageNumber,
    @PathVariable(name = "id") long postId) {

    model.addAttribute("userId", userId);
    model.addAttribute("postsPerPage", postsPerPage);
    model.addAttribute("pageNumber", pageNumber);
    model.addAttribute("post", this.mainService.getPostRep(postId, userId));

    return "submitComment";
  }

  @PostMapping("/users/{userId}/submitComment")
  public String doSubmitComment(
    @PathVariable long userId,
    @RequestParam String title,
    @RequestParam String url,
    @RequestParam long parentId,
    @RequestParam int postsPerPage,
    @RequestParam int pageNumber) {

    this.mainService.save(title, url, userId, parentId);
                  //TODO calculate the precise page number the comment will show up on
                  // (the current method doesn't work in all cases)
    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage + "/page/" + pageNumber;
  }

  @PostMapping("/{userId}/submit")
  public String doSubmit(@PathVariable long userId, @RequestParam String title, @RequestParam String url) {

    this.mainService.save(title, url, userId, 0);
    return "redirect:/forum/" + userId + "/1/page";
  }

  @GetMapping("/{userId}/{id}/increaseScore/{pageNumber}")
  public String increaseScore(@PathVariable long userId, @PathVariable(name="id") long postId, @PathVariable int pageNumber) {
    this.mainService.upvote(postId, userId);
    return "redirect:/forum/"+ userId + "/" + pageNumber +"/page";
  }

  @GetMapping("/{userId}/{id}/decreaseScore/{pageNumber}")
  public String decreaseScore(@PathVariable long userId, @PathVariable long id, @PathVariable int pageNumber) {
    this.mainService.downvote(id, userId);
    return "redirect:/forum/"+ userId + "/" +  pageNumber +"/page";
  }
}
