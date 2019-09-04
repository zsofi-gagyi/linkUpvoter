package com.notReddit.exercise.repository;
import com.notReddit.exercise.model.database.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
  List<Post> findAllByParentId(long parentId);
}
