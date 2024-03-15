package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.entities.RoleEntity;
import org.duyphung.vocamemo.services.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    private RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/add-role")
    public String addRole() {
        roleService.saveRole(new RoleEntity("ROLE_ADMIN"));
        roleService.saveRole(new RoleEntity("ROLE_USER"));
        return "fail";
    }
}
