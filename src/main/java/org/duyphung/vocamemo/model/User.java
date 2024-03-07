package org.duyphung.vocamemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private int targetWordsPerWeek;

    private String password;

    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
    private Role role;

    public User(String userName, String firstName, String lastName, String email, String password, int targetWordsPerWeek) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.targetWordsPerWeek = targetWordsPerWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(targetWordsPerWeek, user.targetWordsPerWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, email, password, targetWordsPerWeek);
    }
}



