package org.duyphung.vocamemo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "definition")
public class DefinitionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "definition")
    private String definition;
    @Column(name = "example")
    private String example;

    @ManyToOne()
    @JoinColumn(name = "meaning_id", nullable = false)
    private MeaningEntity meaningEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefinitionEntity that)) return false;
        return id == that.id && Objects.equals(definition, that.definition) && Objects.equals(example, that.example);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, definition, example);
    }
}
