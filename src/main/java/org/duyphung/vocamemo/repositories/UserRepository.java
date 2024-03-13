package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    UserEntity findUserByUserName(String name);
}
