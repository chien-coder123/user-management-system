package com.ManageUser.ManagerUser.controllers;

import com.ManageUser.ManagerUser.dtos.requests.RoleRequest;
import com.ManageUser.ManagerUser.dtos.requests.UpdateRoleRequest;
import com.ManageUser.ManagerUser.dtos.responses.RoleResponse;
import com.ManageUser.ManagerUser.dtos.responses.UserRoleResponse;
import com.ManageUser.ManagerUser.services.Role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class RoleController {
  private final RoleService roleService;

  @GetMapping("/{id}")
  public ResponseEntity<RoleResponse> getRoleId(@PathVariable("id") Long id){
    return ResponseEntity.ok(roleService.getRoleId(id));
  }


  @GetMapping("")
  public ResponseEntity<List<RoleResponse>> getRoleId(){
    return ResponseEntity.ok(roleService.getRoles());
  }

  @PostMapping("")
  public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request){
    return ResponseEntity.ok(roleService.createRole(request));
  }

  @GetMapping("/{id}/users")
  public ResponseEntity<List<UserRoleResponse>> getUsersByRoleId(@PathVariable("id") Long id){
    return ResponseEntity.ok(roleService.getUsersByRoleId(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleResponse> updateRole(@PathVariable("id") Long id,
                                                 @RequestBody UpdateRoleRequest request){
    return ResponseEntity.ok(roleService.updateRole(id,request));
  }

  @DeleteMapping("/{roleId}/permissions/{permissionId}")
  public ResponseEntity<String> removeRoleFromUser(@PathVariable("roleId") Long roleId,
                                                   @PathVariable("permissionId") Long permissionId)
  {
    roleService.removePermissionfromRole(roleId,permissionId);
    return ResponseEntity.ok("Permission move successfully from role = " + roleId);
  }

  @PostMapping("/{roleId}/permissions/{permissionId}")
  public ResponseEntity<String> assignPermissionToRole(@PathVariable Long roleId,
                                                 @PathVariable Long permissionId)
  {
    roleService.assignPermissionToRole(roleId,permissionId);
    return ResponseEntity.ok("Permission assign successfully to Role have Id = " + roleId);
  }
}
