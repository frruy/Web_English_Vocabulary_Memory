package org.duyphung.vocamemo.models;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(targetEntity = RoleEntity.class)
    private RoleEntity role;

    @ManyToMany(mappedBy = "users")
    private Set<WordEntity> words;

    public UserEntity(String userName, String firstName, String lastName, String email, String phone, String zip, String password ) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.zip = zip;
        this.password = password;

    }

    public UserEntity() {

    }

    public void addWord(WordEntity wordEntity) {
        words.add(wordEntity);
    }

    public void removeWord(WordEntity wordEntity) {
        words.remove(wordEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity user)) return false;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(zip, user.zip) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, email, phone, zip, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}



