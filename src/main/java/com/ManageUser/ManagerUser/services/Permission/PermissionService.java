package com.ManageUser.ManagerUser.services.Permission;

import com.ManageUser.ManagerUser.dtos.requests.PermissionRequest;
import com.ManageUser.ManagerUser.dtos.requests.RoleRequest;
import com.ManageUser.ManagerUser.dtos.responses.PermissionResponse;
import com.ManageUser.ManagerUser.models.Permission;
import com.ManageUser.ManagerUser.repositories.PermissionRepository;
import com.fasterxml.jackson.core.PrettyPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
  private final PermissionRepository permissionRepository;

  public List<PermissionResponse> getPermissions(){
    List<Permission> permissions = permissionRepository.findAll();


    return permissions
        .stream()
        .map(x->
            new PermissionResponse(x.getId(),x.getName()))
        .toList();
  }

  public PermissionResponse createPermission(PermissionRequest request){
    if(permissionRepository.existsByName(request.getName())){
      throw new RuntimeException("Permission name existed");
    }

    Permission permission = Permission.builder()
        .name(request.getName())
        .build();

    permissionRepository.save(permission);

    PermissionResponse permissionResponse = new PermissionResponse();
    BeanUtils.copyProperties(permission, permissionResponse);

    return permissionResponse;
  }

  public PermissionResponse updatePermission(Long id , PermissionRequest request){
    Permission permission = permissionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Permission not found"));

    if(permissionRepository.existsByName(request.getName())){
      throw new IllegalArgumentException("Permission name existed");
    }

    permission.setName(request.getName());

    PermissionResponse permissionResponse = new PermissionResponse();
    BeanUtils.copyProperties(permission, permissionResponse);

    return permissionResponse;
  }

  public void deletePermission(Long id){
    permissionRepository.deleteById(id);
  }
}
