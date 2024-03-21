package org.duyphung.vocamemo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "review")
public class ReviewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "reviewed_at")
    private Timestamp reviewedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "review_word_user",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "word_id", referencedColumnName = "word_id"),
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            }
    )
    private List<WordUser> wordUserSet;

    @Transient
    private List<WordEntity> words;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewEntity that)) return false;
        return id == that.id && Objects.equals(reviewedAt, that.reviewedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewedAt);
    }
}
