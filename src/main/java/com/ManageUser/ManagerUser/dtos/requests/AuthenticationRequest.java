package com.ManageUser.ManagerUser.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
  private String userName;
  private String password;
}
