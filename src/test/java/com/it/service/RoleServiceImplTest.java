package com.it.service;

import com.it.component.LocalizedMessageSource;
import com.it.model.Role;
import com.it.repository.RoleRepository;
import com.it.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Contains tests for RoleServiceImpl class
 */
@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testFindAll() {
        final List<Role> roleList = Collections.singletonList(new Role());
        when(roleRepository.findAll()).thenReturn(roleList);
        assertEquals(roleList, roleService.findAll());
    }

    @Test
    public void testFindById() {
        final Role role = new Role();
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        assertDoesNotThrow(() -> roleService.findById(1L));
        assertEquals(role, roleService.findById(1L));
    }

    @Test
    public void testFindByName() {
        final Role role = new Role();
        when(roleRepository.findByName(anyString())).thenReturn(role);
        assertEquals(role, roleService.findByName("ROLE_USER"));
    }

    @Test
    public void testSave() {
        final Role role = new Role();
        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(role);
        assertEquals(role, roleService.save(role));
    }

    @Test
    public void testUpdate() {
        final Role role = new Role(1L);
        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(role);
        assertEquals(role, roleService.update(role));
    }

    @Test
    public void testDelete() {
        final Role role = new Role();
        role.setId(1L);
        doNothing().when(roleRepository).delete(role);
        assertDoesNotThrow(() -> roleService.delete(role));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(roleRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> roleService.deleteById(1L));
    }
}
