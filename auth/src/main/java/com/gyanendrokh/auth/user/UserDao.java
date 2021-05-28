package com.gyanendrokh.auth.user;

import java.util.Optional;

public interface UserDao {

  Optional<UserEntity> findUserByUsername(String username);

  boolean isUsernameExist(String username);
}
