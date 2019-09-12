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

  public List<PostView> translatePostListToRepList(List<Post> rawResults, User user) {
    List<PostView> result = new ArrayList<>();

    for (Post post : rawResults) {
      PostView postView = translatePostToRep(rawResults, post, user);
      result.add(postView);
    }
    return result;
  }

  public PostView translatePostToRep(List<Post> rawResults, Post post, User user) {
    PostView postView = new PostView(post.getId(), post.getTitle(), post.getUrl(), post.getScore(),
      post.getAuthor().getName(), post.getParentId() != 0);

    if (user == null) {
      postView.setCanBeDownvoted(false);
      postView.setCanBeUpvoted(false);
    } else {
      postView.setCanBeDownvoted(!user.getDownvotes().contains(post));
      postView.setCanBeUpvoted(!user.getUpvotes().contains(post));
    }

    String timeAgo = this.timeTranslator.describePeriodSince(post.getCreationDate());
    postView.setTimeAgo(timeAgo);

    List<Post> comments = rawResults.stream()
      .filter(p -> p.getParentId() == post.getId())
      .sorted(Comparator.comparingInt((Post::getScore)).reversed())
      .collect(Collectors.toList());

    List<PostView> children = translatePostListToRepList(comments, user);

    postView.setChildren(children);
    return postView;
  }
}
