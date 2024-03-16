package org.duyphung.vocamemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class WordUserId implements Serializable {
    @Column(name = "word_id")
    private Integer wordId;

    @Column(name = "user_id")
    private Integer userId;

    public WordUserId(int wordId, int userId) {
        this.wordId = wordId;
        this.userId = userId;
    }

    public WordUserId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordUserId that)) return false;
        return Objects.equals(wordId, that.wordId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordId, userId);
    }
}
