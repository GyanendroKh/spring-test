package com.gyanendrokh.auth.user;

import java.util.Optional;

public interface BaseUserDao {

  Optional<BaseUserEntity> findUserByUsername(String username);

  boolean isUsernameExist(String username);
}
