package com.example.common;

import com.github.javafaker.Faker;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  UUID uuid;
  String name;

  public static Customer fake() {
    return new Customer(
      UUID.randomUUID(),
      Faker.instance().name().name()
    );
  }
}
