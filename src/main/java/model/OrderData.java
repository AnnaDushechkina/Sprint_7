package model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderData {

  private String firstName;
  private String lastName;
  private String address;
  private String metroStation;
  private String phone;
  private Number rentTime;
  private String deliveryDate;
  private String comment;
  private String[] colour;

}