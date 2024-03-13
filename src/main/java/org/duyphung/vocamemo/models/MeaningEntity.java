package org.duyphung.vocamemo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "meaning")
public class MeaningEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "partOfSpeech")
    private String partOfSpeech;
    @OneToMany(mappedBy = "meaningEntity", cascade = CascadeType.ALL, targetEntity = DefinitionEntity.class)
    private List<DefinitionEntity> definitions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity wordEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeaningEntity that)) return false;
        return id == that.id && Objects.equals(partOfSpeech, that.partOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partOfSpeech);
    }
}
