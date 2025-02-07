package com.hust.seller.repository;

import com.hust.seller.entity.Role;
import com.hust.seller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);



}
