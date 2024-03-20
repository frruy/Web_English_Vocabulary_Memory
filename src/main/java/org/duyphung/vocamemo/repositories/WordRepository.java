package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.entities.WordEntity;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {

//    @Query("SELECT w FROM WordEntity w JOIN w.users u JOIN WordUser wu WITH w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId ORDER BY wu.updatedTime DESC")
//    Page<WordEntity> findWordsByUserIdOrderByUpdatedTimeDesc(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT w, wu.isHighlight FROM WordEntity w JOIN w.users u JOIN WordUser wu ON w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId ORDER BY wu.updatedTime DESC")
    List<Object[]> findWordsByUserIdOrderByUpdatedAtDesc(@Param("userId") Integer userId);

    @Query("SELECT w FROM WordEntity w JOIN w.reviews r JOIN w.users u JOIN WordUser wu ON w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId AND wu.isHighlight = true ORDER BY r.reviewedAt ASC LIMIT 7")
    Set<WordEntity> findWordsHighlightByUserIdOrderByReviewedAtAsc(@Param("userId") Integer userId);

    WordEntity findByText(@UniqueElements String text);

    @Modifying
    @Query("UPDATE WordUser wu SET wu.updatedTime = CURRENT_TIMESTAMP WHERE wu.id.wordId = :wordId AND wu.id.userId = :userId")
    void updateUpdatedTime(@Param("wordId") Integer wordId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE WordUser wu SET wu.isHighlight = :isHighlight WHERE wu.id.wordId = :wordId AND wu.id.userId = :userId")
    void updateWordHighlightStatus(@Param("wordId") int wordId, @Param("userId") int userID, @Param("isHighlight") boolean isHighlight);
}
