package com.it.repository;

import com.it.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for Role entity
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Checks by Role entity's name field if such entity exists
     *
     * @param name - name
     * @return - true if exist, falls if not
     */
    boolean existsByName(String name);

    /**
     * Find Role entity by name
     *
     * @param name - name
     * @return - Role entity
     */
    Role findByName(String name);
}
