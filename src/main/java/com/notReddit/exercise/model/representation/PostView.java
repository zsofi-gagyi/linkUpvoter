package com.notReddit.exercise.model.representation;

import java.util.ArrayList;
import java.util.List;

public class PostView {

  long id;
  boolean hasParent;
  String timeAgo;
  String title;
  String url;
  int score;
  String author;
  boolean canBeUpvoted;
  boolean canBeDownvoted;
  List<PostView> children;

  public PostView(long id, String title, String url, int score, String author,  boolean hasParent) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.score = score;
    this.author = author;
    this.hasParent = hasParent;
    this.children = new ArrayList<>();
  }

  public PostView() {
    this.children = new ArrayList<>();
  }

  public String getTimeAgo() {
    return timeAgo;
  }

  public void setTimeAgo(String timeAgo) {
    this.timeAgo = timeAgo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isCanBeUpvoted() {
    return canBeUpvoted;
  }

  public void setCanBeUpvoted(boolean canBeUpvoted) {
    this.canBeUpvoted = canBeUpvoted;
  }

  public boolean isCanBeDownvoted() {
    return canBeDownvoted;
  }

  public void setCanBeDownvoted(boolean canBeDownvoted) {
    this.canBeDownvoted = canBeDownvoted;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<PostView> getChildren() {
    return children;
  }

  public void setChildren(List<PostView> children) {
    this.children = children;
  }

  public boolean hasParent() {
    return hasParent;
  }

  public void setHasParent(boolean hasParent) {
    this.hasParent = hasParent;
  }
}
