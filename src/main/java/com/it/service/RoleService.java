package com.it.service;

import com.it.model.Role;

import java.util.List;

/**
 * Interface with CRUD methods for Role entity
 */
public interface RoleService {

    /**
     * Finds all Role entities
     *
     * @return - List of Role entities
     */
    List<Role> findAll();

    /**
     * Finds the Role entity with the given id
     *
     * @param id - Role entity id
     * @return - Role entity
     */
    Role findById(Long id);

    /**
     * Finds the Role entity by its name
     *
     * @param name - Role entity name
     * @return - Role entity
     */
    Role findByName(String name);

    /**
     * Saves a given Role entity
     *
     * @param role - Role entity
     * @return - the saved Role entity
     */
    Role save(Role role);

    /**
     * Updates a Role entity and flushes changes instantly
     *
     * @param role - Role entity
     * @return - the saved Role entity
     */
    Role update(Role role);

    /**
     * Deletes a given Role entity
     *
     * @param role - Role entity
     */
    void delete(Role role);

    /**
     * Deletes the Role entity with the given id
     *
     * @param id - Role entity id
     */
    void deleteById(Long id);
}
