package com.example.common;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  UUID orderId;
  UUID customerId;
  Date orderDate;

  public static Order fake(UUID customerId) {
    return new Order(
      UUID.randomUUID(),
      customerId,
      new Date()
    );
  }

  public static Order fake() {
    return Order.fake(UUID.randomUUID());
  }
}
