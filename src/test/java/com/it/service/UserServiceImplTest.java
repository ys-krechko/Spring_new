package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Role;
import com.it.model.User;
import com.it.repository.UserRepository;
import com.it.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for UserServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Test
    public void testFindAll() {
        final List<User> userList = Collections.singletonList(new User());
        when(userRepository.findAllWithRole()).thenReturn(userList);
        assertEquals(userList, userService.findAll());
    }

    @Test
    public void testFindById() {
        final User user = new User();
        when(userRepository.findByIdWithRole(anyLong())).thenReturn(Optional.of(user));
        assertEquals(user, userService.findById(1L));
    }

    @Test
    public void testFindByName() {
        final User user = new User();
        when(userRepository.findByName(anyString())).thenReturn(user);
        assertEquals(user, userService.findByName("user.simple"));
    }

    @Test
    public void testSave() {
        final User user = new User();
        final Role role = new Role(1L);
        final Set<Role> roles = Collections.singleton(role);
        user.setRoles(roles);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(roleService.findById(anyLong())).thenReturn(role);
        assertEquals(user, userService.save(user));
    }

    @Test
    public void testUpdate() {
        final User user = new User(1L);
        final Role role = new Role(1L);
        final Set<Role> roles = Collections.singleton(new Role(1L));
        user.setRoles(roles);
        when(userRepository.findByIdWithRole(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(roleService.findById(anyLong())).thenReturn(role);
        assertEquals(user, userService.update(user));
    }

    @Test
    public void testDelete() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findByIdWithRole(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        assertDoesNotThrow(() -> userService.delete(user));
    }

    @Test
    public void testDeleteById() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findByIdWithRole(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> userService.deleteById(1L));
    }

    @Test
    public void testUserNotExistError() {
        when(userRepository.findByIdWithRole(anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> userService.findById(1L));
    }

    @Test
    public void testUserNotHaveIdError() {
        final User user = new User(1L);
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void testUserNameNotUniqueError() {
        final User user = new User();
        user.setName("admin");
        when(userRepository.existsByName("admin")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void testUserRoleIsNullError() {
        final User user = new User();
        assertThrows(RuntimeException.class, () -> userService.save(user));
    }

    @Test
    public void testUserHaveIdError() {
        final User user = new User();
        assertThrows(RuntimeException.class, () -> userService.update(user));
    }
}
