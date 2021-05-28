package com.gyanendrokh.auth.repository;

import com.gyanendrokh.auth.user.BaseUserDao;
import com.gyanendrokh.auth.user.BaseUserEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BaseUserDaoImpl implements BaseUserDao {

  private final BaseUserRepository repository;

  @Override
  public Optional<BaseUserEntity> findUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  @Override
  public boolean isUsernameExist(String username) {
    return repository.findByUsername(username).isPresent();
  }
}
