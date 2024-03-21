package org.duyphung.vocamemo;

import org.duyphung.vocamemo.entities.RoleEntity;
import org.duyphung.vocamemo.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleEntity adminRole;
    private RoleEntity userRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminRole = new RoleEntity(1, "ROLE_ADMIN");
        userRole = new RoleEntity(2, "ROLE_USER");
    }

    @Test
    void testFindRoleByName() {
        when(roleRepository.findRoleByName("ROLE_ADMIN")).thenReturn(adminRole);
        when(roleRepository.findRoleByName("ROLE_USER")).thenReturn(userRole);

        RoleEntity result = roleRepository.findRoleByName("ROLE_ADMIN");
        assertEquals(adminRole, result);

        result = roleRepository.findRoleByName("ROLE_USER");
        assertEquals(userRole, result);
    }

    @Test
    void testFindRoleByUser() {
        long userId = 1L;
        RoleEntity expectedRole = adminRole;

        when(roleRepository.findRoleByUser(userId)).thenReturn(expectedRole);

        RoleEntity result = roleRepository.findRoleByUser(userId);
        assertEquals(expectedRole, result);
    }
}