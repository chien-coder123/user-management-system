package com.ManageUser.ManagerUser.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
  private String userName;
  private String password;
  private String address;
}
