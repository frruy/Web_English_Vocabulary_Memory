package org.duyphung.vocamemo;

import org.duyphung.vocamemo.entities.UserEntity;
import org.duyphung.vocamemo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserEntity user1;
    private UserEntity user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new UserEntity();
        user1.setId(1);
        user1.setEmail("user1@example.com");
        user1.setUserName("user1");

        user2 = new UserEntity();
        user2.setId(2);
        user2.setEmail("user2@example.com");
        user2.setUserName("user2");
    }

    @Test
    void testFindUserByEmail() {
        when(userRepository.findUserByEmail("user1@example.com")).thenReturn(user1);
        when(userRepository.findUserByEmail("user2@example.com")).thenReturn(user2);

        UserEntity result = userRepository.findUserByEmail("user1@example.com");
        assertEquals(user1, result);

        result = userRepository.findUserByEmail("user2@example.com");
        assertEquals(user2, result);
    }

    @Test
    void testFindUserByUserName() {
        when(userRepository.findUserByUserName("user1")).thenReturn(user1);
        when(userRepository.findUserByUserName("user2")).thenReturn(user2);

        UserEntity result = userRepository.findUserByUserName("user1");
        assertEquals(user1, result);

        result = userRepository.findUserByUserName("user2");
        assertEquals(user2, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testFindUserById(int userId) {
        UserEntity expectedUser = userId == 1 ? user1 : user2;

        when(userRepository.findUserById(userId)).thenReturn(expectedUser);

        UserEntity result = userRepository.findUserById(userId);
        assertEquals(expectedUser, result);
    }
}
