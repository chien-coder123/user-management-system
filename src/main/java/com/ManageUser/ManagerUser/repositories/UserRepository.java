package com.ManageUser.ManagerUser.repositories;

import com.ManageUser.ManagerUser.models.Role;
import com.ManageUser.ManagerUser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  boolean existsByUserName(String userName);
  List<User> findByRolesContaining(Role role);
  Optional<User> findByUserName(String userName);
}
