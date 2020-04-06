package com.it.service.impl;

import com.it.component.LocalizedMessageSource;
import com.it.config.utils.CacheName;
import com.it.model.Role;
import com.it.repository.RoleRepository;
import com.it.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of service interface for Role entity
 */
@RequiredArgsConstructor
@CacheConfig(cacheNames = {CacheName.ROLE})
@Service
public class RoleServiceImpl implements RoleService {

    private final LocalizedMessageSource localizedMessageSource;
    private final RoleRepository roleRepository;

    /**
     * Finds all Role entities
     *
     * @return - List of Role entities
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Finds the Role entity with the given id
     *
     * @param id - Role entity id
     * @return - Role entity
     */
    @Cacheable(key = "#id")
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.role.notExist", new Object[]{})));
    }

    /**
     * Finds the Role entity by its name
     *
     * @param name - Role entity name
     * @return - Role entity
     */
    @Cacheable(key = "#name")
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Saves a given Role entity
     *
     * @param role - Role entity
     * @return - the saved Role entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Role save(Role role) {
        validate(role.getId() != null, localizedMessageSource.getMessage("error.role.notHaveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()), localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    /**
     * Updates a Role entity and flushes changes instantly
     *
     * @param role - Role entity
     * @return - the saved Role entity
     */
    @CachePut(key = "#result.id")
    @Transactional
    @Override
    public Role update(Role role) {
        final Long roleId = role.getId();
        validate(roleId == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        final boolean isDuplicateExist = duplicateRole != null && !Objects.equals(duplicateRole.getId(), roleId);
        validate(isDuplicateExist, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    /**
     * Deletes a given Role entity
     *
     * @param role - Role entity
     */
    @CacheEvict(key = "#role.id")
    @Transactional
    @Override
    public void delete(Role role) {
        validate(role.getId() == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        roleRepository.delete(role);
    }

    /**
     * Deletes the Role entity with the given id
     *
     * @param id - Role entity id
     */
    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
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
