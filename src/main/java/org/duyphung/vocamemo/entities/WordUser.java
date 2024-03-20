package org.duyphung.vocamemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "word_user")
public class WordUser {
    @EmbeddedId
    private WordUserId id;

    @Column(name = "updated_time")
    private Timestamp updatedTime;

    @Column(name = "is_highlight")
    private Boolean isHighlight;
}
