package org.duyphung.vocamemo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = "word_user")
public class WordUser {
    @EmbeddedId
    private WordUserId id;

    @Column(name = "last_search_time")
    private Timestamp lastSearchTime;

    @Column(name = "is_highlight")
    private Boolean isHighlight = false;

    @Column(name = "last_review_time")
    private Timestamp lastReviewTime;

    @ManyToMany(mappedBy = "wordUserSet", fetch = FetchType.EAGER)
    private Set<ReviewEntity> reviews;
}
