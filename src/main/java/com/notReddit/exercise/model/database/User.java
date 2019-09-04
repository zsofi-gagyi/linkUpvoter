package com.notReddit.exercise.model.database;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "linkUpvoterUser")
public class User {

  @Id
  @Column(name="user_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  long id;

  @Column
  String name;

  @Column
  String password;

  @OneToMany(mappedBy = "author")
  List<Post> Posts;

  @ManyToMany(mappedBy = "upvoters")
  List<Post> upvotes;

  @ManyToMany(mappedBy = "downvoters")
  List<Post> downvotes;

  public User(String name, String password) {
    this.name = name;
    this.password = password;
    this.upvotes = new ArrayList<>();
    this.downvotes = new ArrayList<>();
  }

  public User() {
    this.upvotes = new ArrayList<>();
    this.downvotes = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Post> getPosts() {
    return Posts;
  }

  public void setPosts(List<Post> posts) {
    this.Posts = posts;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Post> getWritten() {
    return Posts;
  }

  public void setWritten(List<Post> written) {
    this.Posts = written;
  }

  public List<Post> getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(List<Post> upvotes) {
    this.upvotes = upvotes;
  }

  public List<Post> getDownvotes() {
    return downvotes;
  }

  public void setDownvotes(List<Post> downvotes) {
    this.downvotes = downvotes;
  }
}
