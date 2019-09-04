package com.notReddit.exercise.service.helper;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.service.repositoryRelated.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Paginator {

  @Autowired
  PostService postService;

  public List<List<Post>> allPostsPaginated(List<Post> allPosts, int postsPerPage) {
    List<List<Post>> result = new ArrayList<>();

    List<Post> postsWithFamiliesSorted = allPosts.stream()
      .sorted(Comparator.comparingInt(Post::getScore).reversed())
      .filter(p -> p.getParentId() == 0)
      .flatMap(p -> getWholeFamily(p).stream())
      .collect(Collectors.toList());

    while (!postsWithFamiliesSorted.isEmpty()) {
      List<Post> pageOfPostsWithoutFamilies = new ArrayList<>();
      int shown = 0;

      while (shown < postsPerPage && !postsWithFamiliesSorted.isEmpty()) {
        Post post = postsWithFamiliesSorted.get(0);
        postsWithFamiliesSorted.remove(0);

        if (post.getParentId() == 0) {
          pageOfPostsWithoutFamilies.add(post);
        }
        shown++;
      }

      if (!pageOfPostsWithoutFamilies.isEmpty()) {
        result.add(pageOfPostsWithoutFamilies);
      }
    }
    return result;
  }

  private List<Post> getWholeFamily(Post post) {
    List<Post> family = new ArrayList<>();
    family.add(post);
    family.addAll(descendantsIfExist(post));
    return family;
  }

  private List<Post> descendantsIfExist(Post post) {
    List<Post> descendants = new ArrayList<>();
    List<Post> children = this.postService.findAllByParentId(post.getId());

    descendants.addAll(children);

    if (!children.isEmpty()) {
      for (Post child : children) {
        descendants.addAll(descendantsIfExist(child));
      }
    }

    return descendants;
  }
}
