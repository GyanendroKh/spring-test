package com.gyanendrokh.auth.repository;

import com.gyanendrokh.auth.user.UserDao;
import com.gyanendrokh.auth.user.UserEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

  private final UserRepository repository;

  @Override
  public Optional<UserEntity> findUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  @Override
  public boolean isUsernameExist(String username) {
    return repository.findByUsername(username).isPresent();
  }
}
