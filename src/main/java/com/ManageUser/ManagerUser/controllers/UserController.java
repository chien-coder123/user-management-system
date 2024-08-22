package com.ManageUser.ManagerUser.controllers;

import com.ManageUser.ManagerUser.dtos.requests.UserCreateRequest;
import com.ManageUser.ManagerUser.dtos.requests.UserUpdateRequest;
import com.ManageUser.ManagerUser.dtos.responses.UserResponse;
import com.ManageUser.ManagerUser.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UserController {
  private final UserService userService;


  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserId(@PathVariable("id") Long id){
      return ResponseEntity.ok(userService.getUserId(id));

  }

//  @PreAuthorize("#userName == authentication.principal.username")
//  @PostAuthorize("returnObject.userName == authentication.principal.nickName")
  @GetMapping("/myInfo")
  public ResponseEntity<UserResponse> getMyInfo(){
    return ResponseEntity.ok(userService.getMyInfo());

  }

  @PreAuthorize("hasAnyAuthority('user:getUser','ROLE_ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<UserResponse>> getUsers (){
    return ResponseEntity.ok(userService.getUsers());

  }

  @PostMapping("")
  public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request){
    return ResponseEntity.ok(userService.createUser(request));
  }


  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id,
                                                 @RequestBody UserUpdateRequest request)
  {
    return ResponseEntity.ok(userService.updateUser(id,request));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
    userService.deleteUser(id);
    return ResponseEntity.ok("Deleted successfully");
  }

  @DeleteMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<String> removeRoleFromUser(@PathVariable("userId") Long userId,
                                                   @PathVariable("roleId") Long roleId)
  {
    userService.removeRoleFromUser(userId,roleId);
    return ResponseEntity.ok("Role move successfully from user = " + userId);
  }


  @PostMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<String> assignRoleToUser(@PathVariable Long userId,
                                                 @PathVariable Long roleId)
  {
    userService.assignRoleToUser(userId,roleId);
    return ResponseEntity.ok("Role assign successfully to user have Id = " + userId);
  }

}
