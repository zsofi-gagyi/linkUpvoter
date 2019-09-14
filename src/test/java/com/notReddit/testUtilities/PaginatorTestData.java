package com.notReddit.testUtilities;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PaginatorTestData {
  public String postTitle;
  public int pageNumber;

  private PaginatorTestData(String postTitle, int pageNumber) {
    this.postTitle = postTitle;
    this.pageNumber = pageNumber;
  }

  public static List<PaginatorTestData> getPaginationForTwoPerPage(){
    List<PaginatorTestData> result = new ArrayList<>();
    result.add( new PaginatorTestData("1", 1));
    result.add( new PaginatorTestData("2", 1));
    result.add( new PaginatorTestData("3", 2));
    result.add( new PaginatorTestData("4", 3));
    result.add( new PaginatorTestData("5", 3));
    result.add( new PaginatorTestData("6", 4));
    return result;
  }

  public static List<Post> getAllPosts() {
    User testUser = new User();
    List<Post> allPosts = new ArrayList<>();

    String[] titles = {"1", "2", "3", "4", "5", "6"};
    for (int i = 0; i < 6; i++) {
      allPosts.add(new Post(i + 1, (7 - i), titles[i], testUser, 0L));
    }

    allPosts.add(new Post(8L, 7, "3.1", testUser, 3L));
    allPosts.add(new Post(9L, 8, "3.1.1", testUser, 8L));
    allPosts.add(new Post(10L, 4, "5.1", testUser, 5L));

    allPosts.sort(Comparator.comparingLong(Post::getId));
    return allPosts;

    // when arranged by score and paginated with minimum 2 posts / page, these posts look like this:
    //  |1

    //  |2
    //-----------
    //  |3
    //  ||3.1
    //  |||3.1.1
    //-----------
    //  |4

    //  |5
    //  ||5.1
    //-----------
    //  |6
  }
}
