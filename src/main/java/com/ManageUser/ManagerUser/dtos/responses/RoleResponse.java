package com.ManageUser.ManagerUser.dtos.responses;


import com.ManageUser.ManagerUser.models.Permission;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
  private Long id;
  private String name;
  private List<Permission> permissions;
}
