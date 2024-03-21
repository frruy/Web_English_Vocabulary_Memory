package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.entities.WordEntity;
import org.duyphung.vocamemo.entities.WordUser;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {

//    @Query("SELECT w FROM WordEntity w JOIN w.users u JOIN WordUser wu WITH w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId ORDER BY wu.updatedTime DESC")
//    Page<WordEntity> findWordsByUserIdOrderByUpdatedTimeDesc(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT w, wu.isHighlight FROM WordEntity w JOIN w.users u JOIN WordUser wu ON w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId ORDER BY wu.lastSearchTime DESC")
    List<Object[]> findWordsByUserIdOrderByLastSearchTimeDesc(@Param("userId") Integer userId);

    @Query("SELECT w FROM WordEntity w JOIN w.users u JOIN WordUser wu ON w.id = wu.id.wordId AND u.id = wu.id.userId WHERE u.id = :userId AND wu.isHighlight = true ORDER BY wu.lastReviewTime ASC")
    List<WordEntity> findWordsHighlightByUserIdOrderByReviewedAt(@Param("userId") Integer userId, Pageable pageable);

    WordEntity findByText(@UniqueElements String text);

    List<WordEntity> findByTextIn(Collection<String> text);

    @Modifying
    @Query("UPDATE WordUser wu SET wu.lastSearchTime = CURRENT_TIMESTAMP WHERE wu.id.wordId = :wordId AND wu.id.userId = :userId")
    void updateUpdatedTime(@Param("wordId") Integer wordId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("update WordUser wu set wu.lastReviewTime = CURRENT_TIMESTAMP where wu.id.userId = :userId and wu.id.wordId in :wordIds")
    void updateReviewTime(@Param("wordIds") List<Integer> wordIds, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE WordUser wu SET wu.isHighlight = :isHighlight WHERE wu.id.wordId = :wordId AND wu.id.userId = :userId")
    void updateWordHighlightStatus(@Param("wordId") int wordId, @Param("userId") int userID, @Param("isHighlight") boolean isHighlight);

    @Query("SELECT wu FROM WordUser wu WHERE wu.id.userId = :userId AND wu.id.wordId IN :wordIds")
    List<WordUser> findWordUsersByWords(@Param("wordIds") List<Integer> wordIds, int userId);
}
