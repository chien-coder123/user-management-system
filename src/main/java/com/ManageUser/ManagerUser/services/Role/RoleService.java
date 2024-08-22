package com.ManageUser.ManagerUser.services.Role;

import com.ManageUser.ManagerUser.dtos.requests.RoleRequest;
import com.ManageUser.ManagerUser.dtos.requests.UpdateRoleRequest;
import com.ManageUser.ManagerUser.dtos.responses.RoleResponse;
import com.ManageUser.ManagerUser.dtos.responses.UserResponse;
import com.ManageUser.ManagerUser.dtos.responses.UserRoleResponse;
import com.ManageUser.ManagerUser.models.Permission;
import com.ManageUser.ManagerUser.models.Role;
import com.ManageUser.ManagerUser.models.User;
import com.ManageUser.ManagerUser.repositories.PermissionRepository;
import com.ManageUser.ManagerUser.repositories.RoleRepository;
import com.ManageUser.ManagerUser.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;
  private final UserRepository userRepository;

  public RoleResponse getRoleId(Long id ){
    Role role = roleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    RoleResponse roleResponse  = new RoleResponse();
    BeanUtils.copyProperties(role, roleResponse);

    return roleResponse;
  }

  public List<RoleResponse> getRoles(){
    List<Role> roles = roleRepository.findAll();
    return roles
        .stream()
        .map(x -> new RoleResponse(x.getId(),x.getName(),x.getPermissions()))
        .toList();
  }

  public RoleResponse createRole(RoleRequest request){
    if(roleRepository.existsByName(request.getName())){
      throw new RuntimeException("Role existed");
    }

    Role role = new Role();
    BeanUtils.copyProperties(request,role);

    var permissions = permissionRepository.findAllByNameIn(request.getPermissions());
    role.setPermissions(permissions);

    roleRepository.save(role);

    RoleResponse response = new RoleResponse();
    BeanUtils.copyProperties(role,response);
    return response;
  }

  public List<UserRoleResponse>  getUsersByRoleId(Long roleId){
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    List<User> users = userRepository.findByRolesContaining(role);
    List<UserRoleResponse> userRoleResponses = users.stream()
        .map(x -> new UserRoleResponse(x.getId(),x.getUserName(),x.getAddress()))
        .toList();

    return userRoleResponses;
  }

  public RoleResponse updateRole(Long id, UpdateRoleRequest request){
     Role role = roleRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Role not found"));

      var permissions = permissionRepository.findAllByNameIn(request.getPermissions());
      role.setPermissions(permissions);
      roleRepository.save(role);

      RoleResponse roleResponse = new RoleResponse();
      BeanUtils.copyProperties(role,roleResponse);

      return roleResponse;
  }

  @Transactional
  public void removePermissionfromRole(Long roleId, Long permissionId){
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found"));

    Permission permission = permissionRepository.findById(permissionId)
        .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

    if(role.getPermissions().contains(permission)){
      role.getPermissions().remove(permission);

      roleRepository.save(role);
    }
  }

  @Transactional
  public void assignPermissionToRole(Long roleId, Long permissionId){
    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    Permission permission = permissionRepository.findById(permissionId)
        .orElseThrow(() -> new RuntimeException("Permission not found"));

   if(!role.getPermissions().contains(permission)){
     role.getPermissions().add(permission);

     roleRepository.save(role);
   }
  }
}
