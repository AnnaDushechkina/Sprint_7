package model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderFullData {

  private Number id;
  private String firstName;
  private String lastName;
  private String address;
  private String metroStation;
  private String phone;
  private Number rentTime;
  private String deliveryDate;
  private Number track;
  private Number status;
  private String[] colour;
  private String comment;
  private Boolean cancelled;
  private Boolean finished;
  private Boolean inDelivery;
  private String courierFirstName;
  private String createdAt;
  private String updatedAt;

}
