package com.ManageUser.ManagerUser.repositories;

import com.ManageUser.ManagerUser.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
  boolean existsByName(String name);

}
