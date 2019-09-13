package com.notReddit.exercise.model.representation;

import com.notReddit.exercise.model.database.Post;

import java.util.ArrayList;
import java.util.List;

public class PagesView {
  public List<Post> posts;
  public List<Integer> pageNumbers;

  public PagesView(List<Post> posts, int maxPageNumber) {
    this.posts = posts;
    this.pageNumbers = new ArrayList<>();

    int currentPageNumber = 1;
    do {
      pageNumbers.add(currentPageNumber);
      currentPageNumber++;
    } while (currentPageNumber <= maxPageNumber);
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public List<Integer> getPageNumbers() {
    return pageNumbers;
  }

  public void setPageNumbers(List<Integer> pageNumbers) {
    this.pageNumbers = pageNumbers;
  }
}
