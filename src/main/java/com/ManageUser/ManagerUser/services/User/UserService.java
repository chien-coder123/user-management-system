package com.ManageUser.ManagerUser.services.User;

import com.ManageUser.ManagerUser.dtos.requests.UserCreateRequest;
import com.ManageUser.ManagerUser.dtos.requests.UserUpdateRequest;
import com.ManageUser.ManagerUser.dtos.responses.UserResponse;
import com.ManageUser.ManagerUser.models.Role;
import com.ManageUser.ManagerUser.models.User;
import com.ManageUser.ManagerUser.repositories.RoleRepository;
import com.ManageUser.ManagerUser.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
//  private final PasswordEncoder passwordEncoder;

//  @PostAuthorize("returnObject.user == authentication.name")
  public UserResponse getUserId(Long id){
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("user not found"));

    UserResponse userResponse = new UserResponse();
    BeanUtils.copyProperties(user, userResponse);
    return userResponse;
  }

  public UserResponse getMyInfo(){
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();

    User user = userRepository.findByUserName(name)
        .orElseThrow(() -> new RuntimeException("User not existed"));

    UserResponse  userResponse = new UserResponse();
    BeanUtils.copyProperties(user, userResponse);

    return userResponse;
  }

  public List<UserResponse> getUsers(){
    List<User> users = userRepository.findAll();

    List<UserResponse> userResponses = users
        .stream()
        .map(x -> new UserResponse(x.getId(),
                                  x.getUserName(),
                                  x.getPassword(),
                                  x.getAddress(),
                                  x.getRoles()))
        .collect(Collectors.toList());

    return userResponses;
  }

  public UserResponse createUser(UserCreateRequest request){
    if(userRepository.existsByUserName(request.getUserName())){
      throw new RuntimeException("User name existed");
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    User user = User.builder()
        .userName(request.getUserName())
        .password(passwordEncoder.encode(request.getPassword()))
        .address(request.getAddress())
        .build();

    userRepository.save(user);

    UserResponse userResponse = new UserResponse();

    BeanUtils.copyProperties(user,userResponse);
    return userResponse;
  }

  public UserResponse updateUser(Long id ,UserUpdateRequest request){
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setAddress(request.getAddress());

    userRepository.save(user);

    UserResponse userResponse = new UserResponse();
    BeanUtils.copyProperties(user,userResponse);

    return userResponse;
  }

  public void deleteUser(Long id){
    userRepository.deleteById(id);
  }

  @Transactional
  public void removeRoleFromUser(Long userId, Long roleId){
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new IllegalArgumentException("Role not found"));

    if(user.getRoles().contains(role)){
      user.getRoles().remove(role);

      userRepository.save(user);
    }
  }

  @Transactional
  public void assignRoleToUser(Long userId, Long roleId){
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Role role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    if(!user.getRoles().contains(role)){
      user.getRoles().add(role);
      userRepository.save(user);
    }
  }
}
