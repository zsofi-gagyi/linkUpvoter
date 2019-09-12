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

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/submitPost")
  public String getSubmit(Model model,
    @PathVariable long userId,
    @PathVariable Integer postsPerPage) {

    model.addAttribute("userId", userId);
    model.addAttribute("postsPerPage", postsPerPage);
    return "submitPost";
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}/posts/{id}/submitComment")
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
                  // (the current method doesn't work perfectly in all cases)
    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage + "/page/" + pageNumber;
  }

  @PostMapping("users/{userId}/postsPerPage/{postsPerPage}/submitPost")
  public String doSubmit(
    @PathVariable long userId,
    @PathVariable int postsPerPage,
    @RequestParam String title,
    @RequestParam String url) {

    this.mainService.save(title, url, userId, 0);
                    //TODO show the page the user's new post is on
    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage +"/page/1";
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}/posts/{id}/{scoreModifying}")
  public String modifyScore(
    @PathVariable Long userId,
    @PathVariable Integer postsPerPage,
    @PathVariable Integer pageNumber,
    @PathVariable(name="id") long postId,
    @PathVariable String scoreModifying) {

    if (scoreModifying.equals("increaseScore")){
      this.mainService.upvote(postId, userId);
    } else {
      this.mainService.downvote(postId, userId);
    }

    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage +"/page/" + pageNumber;
  }
}
