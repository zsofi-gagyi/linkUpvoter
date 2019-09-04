package com.notReddit.exercise.service.repositoryRelated;

import com.notReddit.exercise.model.database.Post;
import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

  @Autowired
  PostRepository postRepository;

  public Post getPost(long id) {
    return this.postRepository.findById(id).orElse(null);
  }

  public List<Post> findAll() {
    List<Post> allPosts = new ArrayList<>();
    this.postRepository.findAll().forEach(allPosts::add);
    return allPosts;
  }

  public List<Post> findAllByParentId(long parentId){
    return this.postRepository.findAllByParentId(parentId);
  }

  public void save(String title, String url, User User, long parentId) {
    this.postRepository.save(new Post(title, url, User, parentId));
  }

  public void save(Post Post) {
    this.postRepository.save(Post);
  }
}
