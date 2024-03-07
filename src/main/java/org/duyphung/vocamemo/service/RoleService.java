package org.duyphung.vocamemo.service;


import org.duyphung.vocamemo.model.Role;

import java.util.List;

public interface RoleService {
    public void saveRole(Role role);
    public Role findRoleByRoleName(String name);
    public List<Role> getAllRoles();
    public Role getRolesByUser(long id);
}