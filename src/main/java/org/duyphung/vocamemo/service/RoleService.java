package org.duyphung.vocamemo.service;


import org.duyphung.vocamemo.model.RoleEntity;

import java.util.List;

public interface RoleService {
    public void saveRole(RoleEntity role);
    public RoleEntity findRoleByRoleName(String name);
    public List<RoleEntity> getAllRoles();
    public RoleEntity getRolesByUser(long id);
}