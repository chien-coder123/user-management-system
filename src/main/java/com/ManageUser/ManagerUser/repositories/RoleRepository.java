package com.ManageUser.ManagerUser.repositories;

import com.ManageUser.ManagerUser.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
  boolean existsByName(String name);
  List<Role> findAllByNameIn(List<String> names);
  Optional<Role> findByName(String name);
}
