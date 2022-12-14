package com.kevinjanvier.multitenant.repository;

import com.kevinjanvier.multitenant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByTenantId(String tenantId);
}
