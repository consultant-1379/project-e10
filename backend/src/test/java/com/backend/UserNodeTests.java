package com.backend;

import com.backend.entities.UserNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class UserNodeTests {
    @Test
     void testUserNodeConstructor() {
        UserNode userNode = new UserNode();
        assertNotNull(userNode);
    }

    @Test
     void testSetAndGetUserId() {
        UserNode userNode = new UserNode();

        userNode.setUserId(1);
        assertEquals(1, userNode.getUserId());
    }

    @Test
     void testSetAndSessionId() {
        UserNode userNode = new UserNode();

        userNode.setSessionId(2);
        assertEquals(2, userNode.getSessionId());
    }

    @Test
     void testSetAndGetSessionPin() {
        UserNode userNode = new UserNode();

        userNode.setSessionPin(3);
        assertEquals(3, userNode.getSessionPin());
    }
}

