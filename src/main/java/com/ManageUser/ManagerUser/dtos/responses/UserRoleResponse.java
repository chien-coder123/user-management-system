package com.ManageUser.ManagerUser.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRoleResponse {
  private Long id;
  private String userName;
  private String address;
}
