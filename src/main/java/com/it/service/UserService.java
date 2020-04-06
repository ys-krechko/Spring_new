package com.it.service;

import com.it.model.User;

import java.util.List;

/**
 * Interface with CRUD methods for User entity
 */
public interface UserService {

    /**
     * Finds all User entities
     *
     * @return - List of User entities
     */
    List<User> findAll();

    /**
     * Finds the User entity with the given id
     *
     * @param id - User entity id
     * @return - User entity
     */
    User findById(Long id);

    /**
     * Finds the User entity by its name
     *
     * @param name - User's name
     * @return - User entity
     */
    User findByName(String name);

    /**
     * Saves a given User entity
     *
     * @param user - User entity
     * @return - the saved User entity
     */
    User save(User user);

    /**
     * Updates a User entity and flushes changes instantly
     *
     * @param user - User entity
     * @return - the updated User entity
     */
    User update(User user);

    /**
     * Deletes a given User entity
     *
     * @param user - User entity
     */
    void delete(User user);

    /**
     * Deletes the User entity with the given id
     *
     * @param id - User entity id
     */
    void deleteById(Long id);

    /**
     * Gets redirect url for save method
     *
     * @return - redirect url in String form
     */
    String getRedirectUrl();

    /**
     * Changes password for specified User
     *
     * @param user        - User who's password needs to be changed
     * @param newPassword - new password provided by User
     * @param oldPassword - old password provided by User
     */
    void changePassword(User user, String newPassword, String oldPassword);
}
