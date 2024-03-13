package org.duyphung.vocamemo.repositories;

import org.duyphung.vocamemo.models.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {

}
