package com.notReddit.exercise.controller;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

  @Autowired
  private MainService mainService;

  private enum ScoreModifying {upvote, downvote}

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

    User user = this.mainService.getUser(userId);
    this.mainService.saveAndReturnPost(title, url, user, parentId);

    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage + "/page/" + pageNumber;
  }

  @PostMapping("users/{userId}/postsPerPage/{postsPerPage}/submitPost")
  public String doSubmitPost(
    @PathVariable long userId,
    @PathVariable int postsPerPage,
    @RequestParam String title,
    @RequestParam String url) {

    User user = this.mainService.getUser(userId);
    Post post = this.mainService.saveAndReturnPost(title, url, user, 0);
    int pageNumber = this.mainService.findOnWhichPageIsPostThatIsNotComment(postsPerPage, post);

    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage +"/page/" + pageNumber;
  }

  @GetMapping("/users/{userId}/postsPerPage/{postsPerPage}/page/{pageNumber}/posts/{id}/{scoreModifying}")
  public String modifyScore(
    @PathVariable Long userId,
    @PathVariable Integer postsPerPage,
    @PathVariable Integer pageNumber,
    @PathVariable(name="id") long postId,
    @PathVariable ScoreModifying scoreModifying) {

    if (scoreModifying.toString().equals("increaseScore")){
      this.mainService.upvote(postId, userId);
    } else {
      this.mainService.downvote(postId, userId);
    }

    return "redirect:/users/" + userId + "/postsPerPage/" + postsPerPage +"/page/" + pageNumber;
  }
}
