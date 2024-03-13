package org.duyphung.vocamemo.services;

import org.duyphung.vocamemo.models.UserEntity;
import org.duyphung.vocamemo.sercurity.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String userName);
    public void create(UserDTO userDTO);
    public UserEntity findUserByEmail(String email);
    public UserEntity findUserByName(String name);
}
