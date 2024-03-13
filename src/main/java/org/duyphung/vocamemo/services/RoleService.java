package org.duyphung.vocamemo.services;


import org.duyphung.vocamemo.models.RoleEntity;

import java.util.List;

public interface RoleService {
    void saveRole(RoleEntity role);

    RoleEntity findRoleByRoleName(String name);

    List<RoleEntity> getAllRoles();
}