package com.ManageUser.ManagerUser.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntrospectRequest {
  private String token;
}
