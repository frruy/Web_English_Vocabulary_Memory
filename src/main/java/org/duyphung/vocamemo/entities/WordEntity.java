package org.duyphung.vocamemo.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import lombok.Data;
import org.duyphung.vocamemo.adapters.WordEntityTypeAdapter;

import java.sql.Timestamp;
import java.util.HashSet;
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

    @Transient
    private boolean isHighlight;

    @OneToMany(mappedBy = "wordEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MeaningEntity> meanings = new HashSet<>();

    @ManyToMany
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
        return id == that.id && Objects.equals(audio, that.audio) && Objects.equals(text, that.text) && Objects.equals(phonetic, that.phonetic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audio, text, phonetic);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WordEntity.class, new WordEntityTypeAdapter())
                .create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id=" + id +
                ", audio='" + audio + '\'' +
                ", text='" + text + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", isHighlight=" + isHighlight +
                '}';
    }
}
