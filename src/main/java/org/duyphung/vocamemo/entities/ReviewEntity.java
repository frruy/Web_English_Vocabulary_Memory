package org.duyphung.vocamemo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
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
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "review_word",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private Set<WordEntity> words;
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
