package com.gyanendrokh.auth.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "User")
public class UserEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(unique = true)
  String username;

  @Column()
  String password;

  public UserEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
