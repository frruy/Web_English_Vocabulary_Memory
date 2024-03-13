package org.duyphung.vocamemo.services;


import org.duyphung.vocamemo.models.RoleEntity;

import java.util.List;

public interface RoleService {
    public void saveRole(RoleEntity role);
    public RoleEntity findRoleByRoleName(String name);
    public List<RoleEntity> getAllRoles();
    public RoleEntity getRolesByUser(long id);
}