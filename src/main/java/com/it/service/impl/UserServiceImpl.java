package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.User;
import com.it.repository.UserRepository;
import com.it.service.RoleService;
import com.it.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for User entity
 */
@CacheConfig(cacheNames = {CacheName.USER})
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final LocalizedMessageSource localizedMessageSource;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Value("${redirect.url.for.save.user}")
    private String redirectUrl;

    /**
     * Finds all User entities
     *
     * @return - List of User entities
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAllWithRole();
    }

    /**
     * Finds the User entity with the given id
     *
     * @param id - User entity id
     * @return - User entity
     */
    @Cacheable(key = "#id")
    @Override
    public User findById(Long id) {
        return userRepository.findByIdWithRole(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.user.notExist", new Object[]{})));
    }

    /**
     * Finds the User entity by its name
     *
     * @param name - User's name
     * @return - User entity
     */
    @Cacheable(key = "#name")
    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Saves a given User entity
     *
     * @param user - User entity
     * @return - the saved User entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public User save(User user) {
        validate(user.getId() != null, localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        validate(userRepository.existsByName(user.getName()), localizedMessageSource.getMessage("error.user.name.notUnique", new Object[]{}));
        return saveAndFlush(user);
    }

    /**
     * Updates a User entity and flushes changes instantly
     *
     * @param user - User entity
     * @return - the updated User entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public User update(User user) {
        final Long userId = user.getId();
        validate(userId == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        findById(userId);
        final User duplicateUserName = userRepository.findByName(user.getName());
        final boolean isDuplicateUserNameExists = duplicateUserName != null && !Objects.equals(duplicateUserName.getId(), userId);
        validate(isDuplicateUserNameExists, localizedMessageSource.getMessage("error.user.name.notUnique", new Object[]{}));
        return saveAndFlush(user);
    }

    /**
     * Deletes a given User entity
     *
     * @param user - User entity
     */
    @CacheEvict(key = "#user.id")
    @Transactional
    @Override
    public void delete(User user) {
        final Long userId = user.getId();
        validate(userId == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        findById(userId);
        userRepository.delete(user);
    }

    /**
     * Deletes the User entity with the given id
     *
     * @param id - User entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    /**
     * Gets redirect url for save method
     *
     * @return - redirect url in String form
     */
    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Changes password for specified User
     *
     * @param user        - User who's password needs to be changed
     * @param newPassword - new password provided by User
     * @param oldPassword - old password provided by User
     */
    @Transactional
    @Override
    public void changePassword(User user, String newPassword, String oldPassword) {
        validate(!checkIfOldPasswordIsValid(oldPassword, user.getPassword()), localizedMessageSource.getMessage("error.user.oldPassword.notValid", new Object[]{}));
        user.setPassword(encoder.encode(newPassword));
        userRepository.saveAndFlush(user);
    }

    /**
     * Validates if User's old password, that is going to be changed, is valid
     *
     * @param passwordFromDataStore  - User's password that is stored in DataStore
     * @param passwordProvidedByUser - User's password provided by User
     * @return - true if provided password equals password from DataStore, false if not
     */
    private boolean checkIfOldPasswordIsValid(String passwordProvidedByUser, String passwordFromDataStore) {
        return encoder.matches(passwordProvidedByUser, passwordFromDataStore);
    }

    /**
     * Sets reference to a Set of Role entities
     * Encrypts password
     * Saves a User entity and flushes changes instantly.
     *
     * @param user - User entity
     * @return - the saved User entity
     */
    private User saveAndFlush(User user) {
        user.getRoles().forEach(role -> {
            validate(role == null || role.getId() == null, localizedMessageSource.getMessage("error.user.role.isNull", new Object[]{}));
            role.setName(roleService.findById(role.getId()).getName());
        });
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    /**
     * Checks, if passed parameter(s) matches the given condition
     *
     * @param expression   - condition for checking
     * @param errorMessage - message to be thrown in RuntimeException, if checking fails
     */
    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
