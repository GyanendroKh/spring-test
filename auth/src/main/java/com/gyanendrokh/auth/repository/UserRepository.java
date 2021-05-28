package com.gyanendrokh.auth.repository;

import com.gyanendrokh.auth.user.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT u FROM UserEntity u WHERE  u.username = ?1")
  Optional<UserEntity> findByUsername(String username);

}
