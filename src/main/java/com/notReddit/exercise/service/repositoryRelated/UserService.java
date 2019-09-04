package com.notReddit.exercise.service.repositoryRelated;

import com.notReddit.exercise.model.database.User;
import com.notReddit.exercise.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUser(String userName) {
    return this.userRepository.findByName(userName).orElse(null);
  }

  public User getUser(long userId) {
    return this.userRepository.findById(userId).orElse(null);
  }

  public void save(User User){
    this.userRepository.save(User);
  }
}
