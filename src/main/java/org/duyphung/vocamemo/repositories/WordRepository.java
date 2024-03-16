package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.entities.UserEntity;
import org.duyphung.vocamemo.entities.WordEntity;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {
//    @Query("SELECT w FROM WordEntity w JOIN w.users u WHERE u IN :users ORDER BY w.updatedAt DESC LIMIT 10")
    @Query("SELECT w FROM WordEntity w")
    Set<WordEntity> findTop5ByUsersOrderByCreatedAtDesc(@Param("users") Set<UserEntity> users);
    WordEntity findByText(@UniqueElements String text);
}
