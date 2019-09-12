package com.notReddit.exercise.service;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.model.representation.PostRep;
import com.notReddit.exercise.service.helper.Paginator;
import com.notReddit.exercise.service.repositoryRelated.PostService;
import com.notReddit.exercise.service.repositoryRelated.UserService;
import com.notReddit.exercise.service.representationRelated.PostRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

  @Autowired
  PostService postService;

  @Autowired
  UserService userService;

  @Autowired
  PostRepService postRepService;

  @Autowired
  Paginator paginator;

  //------------------------------------------------------relay to userService------------------------//

  public boolean userExists(String userName) {
    return this.userService.getUser(userName) != null;
  }

  public boolean passwordIsGood(String userName, String password) {
    return this.userService.getUser(userName).getPassword().equals(password);
  }

  public void createNewUser(String userName, String password) {
    this.userService.save(new User (userName, password));
  }

  public long getUserId(String userName) {
    return this.userService.getUser(userName).getId();
  }

  public User getUser(long id) {
    return this.userService.getUser(id);
  }

  //---------------------------------------------------------use user- AND postService---------------//

  public void save(String title, String url, long userId, long parentId) {
    User User = this.userService.getUser(userId);
    this.postService.save(title, url, User, parentId);
  }

  public void upvote(long postId, long userId) {
    User upvoter = this.userService.getUser(userId);
    Post Post = this.postService.getPost(postId);
    List<User> fans = Post.getUpvoters();
    List<User> notFans = Post.getDownvoters();

    if (!fans.contains(upvoter)) {

      if (notFans.contains(upvoter)) {
        notFans.remove(upvoter);
        Post.setDownvoters(notFans);
      } else {
        fans.add(upvoter);
        Post.setUpvoters(fans);
      }

      Post.setScore(Post.getScore() + 1);
      this.postService.save(Post);
    }
  }

  public void downvote(long postId, long userId) {
    User downvoter = this.userService.getUser(userId);
    Post Post = this.postService.getPost(postId);
    List<User> fans = Post.getUpvoters();
    List<User> notFans = Post.getDownvoters();

    if (!notFans.contains(downvoter)) {

      if (fans.contains(downvoter)) {
        fans.remove(downvoter);
        Post.setUpvoters(fans);
      } else {
        notFans.add(downvoter);
        Post.setDownvoters(notFans);
      }

      Post.setScore(Post.getScore() - 1);
      this.postService.save(Post);
    }
  }

  public List<PostRep> translatePosts(List<Post> rawResults, Long userId) {
    User user =  this.userService.getUser(userId);
    return this.postRepService.translatePostListToRepList(rawResults, user);
  }

  //-------------------------------------------------relay to postService with logic from paginator--------//

  public List<Post> createPage(int pageNumber, int postsPerPage) {
    List<Post> allPosts = this.postService.findAll();

    return this.paginator.allPostsPaginated(allPosts, postsPerPage).get(pageNumber - 1);
  }

  public List<Integer> getPageLinks(int postsPerPage) {
    List<Post> allPosts = this.postService.findAll();

    int nrOfPages = this.paginator.allPostsPaginated(allPosts, postsPerPage).size();

    List<Integer> pages = new ArrayList<>();
    for (int i = 0; i < nrOfPages; i++){
      pages.add(i + 1);
    }

    return pages;
  }

  //---------------------------deal with representation, using user-, post- and postRepService---//

  public PostRep getPostRep(long postId, long userId) {
    Post post = this.postService.getPost(postId);
    User user = this.userService.getUser(userId);
    return this.postRepService.translatePostToRep(post, user);
  }
}
