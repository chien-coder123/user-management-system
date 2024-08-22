package com.ManageUser.ManagerUser.controllers;

import com.ManageUser.ManagerUser.dtos.requests.PermissionRequest;
import com.ManageUser.ManagerUser.dtos.responses.PermissionResponse;
import com.ManageUser.ManagerUser.services.Permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class PermissionController {
  private final PermissionService permissionService;

  @GetMapping("")
  public ResponseEntity<List<PermissionResponse>> getPermissions(){
    return ResponseEntity.ok(permissionService.getPermissions());

  }

  @PostMapping("")
  public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
    return ResponseEntity.ok(permissionService.createPermission(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PermissionResponse> updatePermission(@PathVariable Long id,
                                                             @RequestBody PermissionRequest request)
  {
    return ResponseEntity.ok(permissionService.updatePermission(id,request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePermission(@PathVariable Long id)
  {
    permissionService.deletePermission(id);

    return ResponseEntity.ok("Permission deleted successfully with id = "+id);
  }
}
