package org.duyphung.vocamemo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "word", catalog = "")
public class WordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "audio")
    private String audio;

    @Column(name = "text")
    private String text;

    @Column(name = "isSaved")
    private boolean isSaved;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, targetEntity = ReviewEntity.class)
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, targetEntity = MeaningEntity.class)
    private List<MeaningEntity> meanings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordEntity that)) return false;
        return id == that.id && isSaved == that.isSaved && Objects.equals(audio, that.audio) && Objects.equals(text, that.text) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audio, text, isSaved, createdAt);
    }
}
