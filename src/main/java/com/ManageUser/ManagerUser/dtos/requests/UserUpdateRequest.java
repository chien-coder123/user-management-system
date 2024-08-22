package com.ManageUser.ManagerUser.dtos.requests;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
  private String password;
  private String address;
}
