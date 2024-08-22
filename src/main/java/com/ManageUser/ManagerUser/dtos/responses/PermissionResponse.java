package com.ManageUser.ManagerUser.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
  private Long id;
  private String name;
}
