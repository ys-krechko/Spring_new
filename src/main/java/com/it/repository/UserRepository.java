package com.it.repository;

import com.it.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for User entity
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks by User entity's name field if such entity exists
     *
     * @param name - name
     * @return - true if exist, falls if not
     */
    boolean existsByName(String name);

    /**
     * Select all User entities with joined Role entities
     *
     * @return - List of User entities
     */
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles ORDER BY u.id")
    List<User> findAllWithRole();

    /**
     * Select User entity by its ID with joined Role entity
     *
     * @param id - User entity's ID
     * @return - User entity
     */
    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles WHERE u.id=:id ORDER BY u.id")
    Optional<User> findByIdWithRole(@Param("id") Long id);

    /**
     * Find User entity by name
     *
     * @param name - name
     * @return - User entity
     */
    User findByName(String name);
}
