package org.duyphung.vocamemo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String zip;

    private String password;

    private int targetWordsPerDay;

    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    private RoleEntity role;

    @ManyToMany(mappedBy = "users")
    private Set<WordEntity> words = new HashSet<>();

    @OneToMany(mappedBy = "id.userId")
    private Set<WordUser> wordUsers = new HashSet<>();

    public UserEntity(String userName, String firstName, String lastName, String email, String phone, String zip, String password, Integer targetWordsPerDay) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.zip = zip;
        this.password = password;
        this.targetWordsPerDay = targetWordsPerDay;
    }

    public UserEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity user)) return false;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(zip, user.zip) && Objects.equals(password, user.password) && Objects.equals(targetWordsPerDay, user.targetWordsPerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, email, phone, zip, password, targetWordsPerDay);
    }
}



