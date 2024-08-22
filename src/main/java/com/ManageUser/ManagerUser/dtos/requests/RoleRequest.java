package com.ManageUser.ManagerUser.dtos.requests;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {
  private String name;
  private List<String> permissions;
}
