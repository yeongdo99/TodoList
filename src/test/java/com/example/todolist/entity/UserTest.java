package com.example.todolist.entity;

import com.example.todolist.user.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void testUserEntity(){
        User user = new User();
        user.setUsername("test111");
        assertEquals("test111", user.getUsername());
    }
}
