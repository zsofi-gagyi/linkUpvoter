package com.notReddit.exercise.model.database;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "linkUpvoterPost")
public class Post implements Comparable<Post> {

  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "parent")
  long parentId;

  @Column(updatable = false)
  @CreationTimestamp
  LocalDate creationDate;

  @Column
  String title;

  @Column
  String url;

  @Column
  int score;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn
  User author;

  @ManyToMany(cascade = {CascadeType.ALL})
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "upvotes",
    joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
  List<User> upvoters;

  @ManyToMany(cascade = {CascadeType.ALL})
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "downvotes",
    joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
  List<User> downvoters;

  public Post() {
    this.parentId = 0;
    this.score = 1;
    this.creationDate = LocalDate.now();
    this.upvoters = new ArrayList<>();
    this.downvoters = new ArrayList<>();
  }

  public Post(String title, String url, User author, long parentId) {
    this.parentId = parentId;
    this.title = title;
    this.url = url;
    this.score = 1;
    this.creationDate = LocalDate.now();
    this.author = author;
    this.upvoters = Arrays.asList(author);
    this.downvoters = Arrays.asList(author);
  }

  public Post(long id, int score, String title, User author, long parentId) { // for testing
    this.id = id;
    this.score = score;
    this.parentId = parentId;
    this.title = title;
    this.url = "/";
    this.creationDate = LocalDate.now();
    this.author = author;
    this.upvoters = Arrays.asList(author);
    this.downvoters = Arrays.asList(author);
  }

  @Override
  public int compareTo(Post o) {
    return Integer.compare(this.getScore(), o.getScore());
  }

  //------------------------------------------------------------------//

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = LocalDate.now();
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public List<User> getUpvoters() {
    return upvoters;
  }

  public void setUpvoters(List<User> upvoters) {
    this.upvoters = upvoters;
  }

  public List<User> getDownvoters() {
    return downvoters;
  }

  public void setDownvoters(List<User> downvoters) {
    this.downvoters = downvoters;
  }

  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }


}
