package com.ManageUser.ManagerUser.dtos.responses;

import com.ManageUser.ManagerUser.models.Role;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class UserResponse {
  private Long id;
  private String userName;
  private String password;
  private String address;
  private List<Role> roles;


}
