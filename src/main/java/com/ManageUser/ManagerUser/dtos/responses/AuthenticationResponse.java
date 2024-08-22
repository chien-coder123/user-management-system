package com.ManageUser.ManagerUser.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
  private String token;
  private boolean authenticated;
}
