package org.duyphung.vocamemo.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.duyphung.vocamemo.models.RoleEntity;
import org.duyphung.vocamemo.models.UserEntity;
import org.duyphung.vocamemo.repositories.UserRepository;
import org.duyphung.vocamemo.sercurity.UserDTO;
import org.duyphung.vocamemo.sercurity.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @implNote
 * UserPrincipal class which implements UserDetails interface.
 * This way you get more flexibility and control over user authorization and authentication process.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUserName(userName);
        log.debug(userName);
        if (user == null) {
            log.warn("Invalid username or password {}", userName);

            throw new UsernameNotFoundException("Invalid username or password.");
        }
        // return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),   mapRolesToAuthorities(user.getRoles()));

        return new UserPrincipal(user, roleService.getRolesByUser(user.getId()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    /** Using model mapper helps to avoid extra coding
     * @param userDTO
     */
    @Transactional
    public void create(UserDTO userDTO)
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(roleService.findRoleByRoleName(userDTO.getRole()));

        userRepository.save(user);
    }

    /**    * In this example login and email has the same values @param email @return
     */
    public UserEntity findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public UserEntity findUserByName(String name)
    {
        return userRepository.findUserByUserName(name);
    }
}

