package com.ManageUser.ManagerUser.repositories;

import com.ManageUser.ManagerUser.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
  boolean existsByName(String name);
  List<Permission> findAllByNameIn(List<String> names);
}
