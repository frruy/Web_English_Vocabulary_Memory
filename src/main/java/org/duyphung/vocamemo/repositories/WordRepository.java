package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.entities.UserEntity;
import org.duyphung.vocamemo.entities.WordEntity;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {
    @Query("SELECT w FROM WordEntity w JOIN w.users u JOIN WordUser wu ON w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId ORDER BY wu.updatedTime DESC")
    Set<WordEntity> findTop7WordsByUserIdOrderByUpdatedAtDesc(@Param("userId") Integer userId);
    WordEntity findByText(@UniqueElements String text);
}
