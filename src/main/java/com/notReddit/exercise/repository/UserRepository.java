package com.notReddit.exercise.repository;

import com.notReddit.exercise.model.database.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByName(String name);
}
