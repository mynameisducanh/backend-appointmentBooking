package com.example.backend.repositories;

import com.example.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Phương thức truy vấn tùy chỉnh
    Role findByName(String name);
}
