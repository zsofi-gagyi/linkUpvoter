package com.notReddit.exercise.service.helper;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.model.representation.PostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostViewMaker {

  @Autowired
  TimeTranslator timeTranslator;

  public List<PostView> translatePostListToRepList(List<Post> notTranslatedPosts, User user) {
    List<PostView> result = new ArrayList<>();

    List<Post> postsWithoutParents = notTranslatedPosts.stream()
      .filter(p -> p.getParentId() == 0)
      .collect(Collectors.toList());

    for (Post post :  postsWithoutParents) {
      PostView postView = translatePostToRep(notTranslatedPosts, post, user);
      result.add(postView);
    }
    return result;
  }

  public PostView translatePostToRep(List<Post> notTranslatedPosts, Post post, User user) {
    PostView postView = new PostView(post.getId(), post.getTitle(), post.getUrl(), post.getScore(),
      post.getAuthor().getName(), post.getParentId() != 0L);

    if (user == null) {
      postView.setCanBeDownvoted(false);
      postView.setCanBeUpvoted(false);
    } else {
      postView.setCanBeDownvoted(!user.getDownvotes().contains(post));
      postView.setCanBeUpvoted(!user.getUpvotes().contains(post));
    }

    String timeAgo = this.timeTranslator.describePeriodSince(post.getCreationDate());
    postView.setTimeAgo(timeAgo);

    List<PostView> children = findAndTranslateChildren(notTranslatedPosts, post,  user);
    postView.setChildren(children);

    notTranslatedPosts.remove(post);
    return postView;
  }

  private List<PostView> findAndTranslateChildren(List<Post> notTranslatedPosts, Post parent, User user){
    List<Post> comments = notTranslatedPosts.stream()
      .filter(p -> p.getParentId() == parent.getId())
      .sorted(Comparator.comparingInt((Post::getScore)).reversed())
      .collect(Collectors.toList());

    List<PostView> result = new ArrayList<>();
    for (Post comment : comments){
      result.add(translatePostToRep(notTranslatedPosts, comment, user));
    }

    return result;
  }
}
