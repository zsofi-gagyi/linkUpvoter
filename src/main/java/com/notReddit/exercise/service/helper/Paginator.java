package com.notReddit.exercise.service.helper;

import com.notReddit.exercise.model.database.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Paginator {

  public List<List<Post>> postsExceptCommentsPaginated(List<Post> allPosts, int postsPerPage) {
    List<Post> postsThatAreNotComments = allPosts.stream()
      .filter(p -> p.getParentId() == 0)
      .sorted(Comparator.comparingInt(Post::getScore).reversed())
      .collect(Collectors.toList());

    HashMap<Long, Integer> itemsPerPost = countPostSizeWithComments(postsThatAreNotComments, allPosts);

    return splitPostsWithoutCommentsIntoPages(postsPerPage, postsThatAreNotComments, itemsPerPost);
  }

  private List<List<Post>> splitPostsWithoutCommentsIntoPages(int postsPerPage, List<Post> postsThatAreNotComments,
                                                                             HashMap<Long, Integer> itemsPerPost){
    int postsOnthisPage = 0;
    List<Post> pageOfNotComments = new ArrayList<>();
    List<List<Post>> result = new ArrayList<>();
    int index = 0;

    while ( index < postsThatAreNotComments.size()){
      Post post = postsThatAreNotComments.get(index);

      pageOfNotComments.add(post);
      postsOnthisPage += itemsPerPost.get(post.getId());
      index++;

      if (postsOnthisPage >= postsPerPage){
        postsOnthisPage = 0;

        result.add(pageOfNotComments);
        pageOfNotComments = new ArrayList<>();
      }
    }

    if (!pageOfNotComments.isEmpty()){
      result.add(pageOfNotComments);
    }

    return result;
  }

  public List<Post> addCommentsTo(List<Post> postsWithoutComments, List<Post> allPosts){
    List<Post> result = new ArrayList<>();
    for (Post post : postsWithoutComments){
      result.addAll(addAllCommentsToPost(post, allPosts));
    }

    return result;
  }
                              //TODO reorganize things so the number of its comments is stored in each post instead
  private HashMap<Long, Integer> countPostSizeWithComments(List<Post> postsThatAreNotComments, List<Post> allPosts){
    HashMap<Long, Integer> itemsPerPost = new HashMap<>();

    postsThatAreNotComments
      .forEach(post -> {
        int itemsPerThisPost = addAllCommentsToPost(post, allPosts).size();
        itemsPerPost.put(post.getId(), itemsPerThisPost);
      });

    return itemsPerPost;
  }

  private List<Post> addAllCommentsToPost(Post post, List<Post> allPosts) {
    List<Post> postWithComments = new ArrayList<>();
    postWithComments.add(post);
    postWithComments.addAll(commentsIfExist(post, allPosts));
    return postWithComments;
  }

  private List<Post> commentsIfExist(Post post, List<Post> allPosts) {
    List<Post> descendants = new ArrayList<>();
    List<Post> children = allPosts.stream()
      .filter(p -> p.getParentId() == post.getId())
      .collect(Collectors.toList());

    descendants.addAll(children);

    if (!children.isEmpty()) {
      for (Post child : children) {
        descendants.addAll(commentsIfExist(child, allPosts));
      }
    }

    return descendants;
  }

  public int findOnWhichPageIsPostThatIsNotComment(int postsPerPage, Post post, List<Post> allPosts){
    List<List<Post>> paginated = postsExceptCommentsPaginated(allPosts, postsPerPage);

    int index = 0;

    while (index < allPosts.size()){
      List<Post> curentPage = paginated.get(index);
      if (curentPage.contains(post)){
        return index + 1;
      }
      index++;
    }

    return index;
  }
}
