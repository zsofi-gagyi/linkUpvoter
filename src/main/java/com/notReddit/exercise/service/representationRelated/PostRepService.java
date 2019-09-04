package com.notReddit.exercise.service.representationRelated;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.model.representation.PostRep;
import com.notReddit.exercise.service.helper.TimeTranslator;
import com.notReddit.exercise.service.repositoryRelated.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostRepService {

  @Autowired
  TimeTranslator timeTranslator;

  @Autowired
  PostService postService;

  public List<PostRep> translatePostListToRepList(List<Post> rawResults, User user) {
    List<PostRep> result = new ArrayList<>();

    for (Post post : rawResults) {
      PostRep postRep = translatePostToRep(post, user);
      result.add(postRep);
    }
    return result;
  }

  public PostRep translatePostToRep(Post post, User user) {
    PostRep postRep = new PostRep(post.getId(), post.getTitle(), post.getUrl(), post.getScore(),
      post.getAuthor().getName(), post.getParentId() != 0);

    if (user == null) {
      postRep.setCanBeDownvoted(false);
      postRep.setCanBeUpvoted(false);
    } else {
      postRep.setCanBeDownvoted(!user.getDownvotes().contains(post));
      postRep.setCanBeUpvoted(!user.getUpvotes().contains(post));
    }

    String timeAgo = this.timeTranslator.describePeriodSince(post.getCreationDate());
    postRep.setTimeAgo(timeAgo);

    List<Post> comments = this.postService.findAllByParentId(post.getId()).stream()
      .sorted(Comparator.comparingInt((Post::getScore)).reversed())
      .collect(Collectors.toList());

    List<PostRep> children = translatePostListToRepList(comments, user);

    postRep.setChildren(children);
    return postRep;
  }
}
