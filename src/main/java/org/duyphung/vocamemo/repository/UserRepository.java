package org.duyphung.vocamemo.repository;

import org.duyphung.vocamemo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserByEmail(String email);

    public UserEntity findUserByUserName(String name);
}
