package com.gyanendrokh.auth.repository;

import com.gyanendrokh.auth.user.BaseUserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUserEntity, Long> {

  @Query("SELECT u FROM BaseUserEntity u WHERE  u.username = ?1")
  Optional<BaseUserEntity> findByUsername(String username);

}
