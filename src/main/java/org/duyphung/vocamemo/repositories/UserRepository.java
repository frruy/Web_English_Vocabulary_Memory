package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserByEmail(String email);

    public UserEntity findUserByUserName(String name);
}
