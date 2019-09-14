package com.notReddit.exercise.service;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.model.representation.PagesView;
import com.notReddit.exercise.model.representation.PostView;
import com.notReddit.exercise.service.helper.Paginator;
import com.notReddit.exercise.service.repositoryRelated.PostService;
import com.notReddit.exercise.service.repositoryRelated.UserService;
import com.notReddit.exercise.service.helper.PostViewMaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

  PostService postService;

  UserService userService;

  PostViewMaker postViewMaker;

  Paginator paginator;

  public MainService(PostService postService, UserService userService, PostViewMaker postViewMaker, Paginator paginator) {
    this.postService = postService;
    this.userService = userService;
    this.postViewMaker = postViewMaker;
    this.paginator = paginator;
  }

  //------------------------------------------------------relay to userService------------------------//

  public boolean userExists(String userName) {
    return this.userService.getUser(userName) != null;
  }

  public boolean passwordIsBad(String userName, String password) {
    return !this.userService.getUser(userName).getPassword().equals(password);
  }

  public void createNewUser(String userName, String password) {
    this.userService.save(new User (userName, password));
  }

  public long getUserId(String userName) {
    return this.userService.getUser(userName).getId();
  }

  public User getUser(long userId){
    return this.userService.getUser(userId);
  }

  //------------------------------------------------------relay to postService------------------------//

  public Post saveAndReturnPost(String title, String url, User user, long parentId) {
    return this.postService.saveAndReturnPost(title, url, user, parentId);
  }

  //-----------------------------------------------upvote and downvote using user- AND postService---------------//

  public void upvote(long postId, long userId) {
    User user = this.userService.getUser(userId);
    Post Post = this.postService.getPost(postId);
    List<User> fans = Post.getUpvoters();
    List<User> enemies = Post.getDownvoters();

    if (fans.contains(user)) {
      return;
    }

    if (enemies.contains(user)) {
      enemies.remove(user);
    } else {
      fans.add(user);
    }

    Post.setScore(Post.getScore() + 1);
    this.postService.save(Post);
  }

  public void downvote(long postId, long userId) {
    User user = this.userService.getUser(userId);
    Post Post = this.postService.getPost(postId);
    List<User> fans = Post.getUpvoters();
    List<User> enemies = Post.getDownvoters();

    if (enemies.contains(user)) {
      return;
    }

      if (fans.contains(user)) {
        fans.remove(user);
      } else {
        enemies.add(user);
      }

      Post.setScore(Post.getScore() - 1);
      this.postService.save(Post);
  }

  //------------------------------------deal with paging, using postService and paginator------------------//

  public PagesView createPage(int pageNumber, int postsPerPage) {
    List<Post> allPosts = this.postService.findAll();
    List<List<Post>> allPostsWithoutCommentsPaginated = this.paginator.postsExceptCommentsPaginated(allPosts, postsPerPage);
    List<Post> postsOnThisPageWithoutComments = allPostsWithoutCommentsPaginated.get(pageNumber - 1);

    List<Post> postsOnThisPageWithComments = this.paginator.addCommentsTo(postsOnThisPageWithoutComments, allPosts);
    int maxPageNumber = allPostsWithoutCommentsPaginated.size();

    return new PagesView(postsOnThisPageWithComments, maxPageNumber);
  }

  public int findOnWhichPageIsPostThatIsNotComment(int postsPerPage, Post post){
    List<Post> allPosts = this.postService.findAll();
    return this.paginator.findOnWhichPageIsPostThatIsNotComment(postsPerPage, post, allPosts);
  }

  //----------------------------deal with representation, using userService and postViewMaker---------------//

  public List<PostView> translatePosts(List<Post> rawResults, Long userId) {
    User user =  this.userService.getUser(userId);
    return this.postViewMaker.translatePostListToRepList(rawResults, user);
  }

  //---------------------------deal with representation, using userService, postService and postViewMaker---//

  public PostView getPostRep(long postId, long userId) {
    Post post = this.postService.getPost(postId);
    List<Post> comments = this.postService.findAllByParentId(postId);
    User user = this.userService.getUser(userId);

    return this.postViewMaker.translatePostToRep(comments, post, user);
  }
}
