package com.ManageUser.ManagerUser.dtos.requests;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoleRequest {
  private List<String> permissions;
}
