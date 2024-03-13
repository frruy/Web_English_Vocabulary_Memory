package org.duyphung.vocamemo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "word")
public class WordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "audio")
    private String audio;

    @Column(name = "text")
    private String text;

    @Column(name = "phonetic")
    private String phonetic;

    @Column(name = "isSaved")
    private boolean isSaved;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "word_review",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Set<ReviewEntity> reviews;

    @OneToMany(mappedBy = "wordEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeaningEntity> meanings = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "word_user",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users = new HashSet<>();

    public void addUser(UserEntity user) {
        users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordEntity that)) return false;
        return id == that.id && isSaved == that.isSaved && Objects.equals(audio, that.audio) && Objects.equals(text, that.text) && Objects.equals(phonetic, that.phonetic) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audio, text, phonetic, isSaved, createdAt);
    }
}
