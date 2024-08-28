package com.backend;

import com.backend.entities.DataNode;
import com.backend.entities.UserNode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class DataNodeTests {

    @Test
     void testDateNodeConstructor() {
        DataNode dataNode = new DataNode();
        assertNotNull(dataNode);
    }

    @Test
     void testDateNodeConstructorWithParam() {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("Question 1",responses,user);
        assertNotNull(dataNode);
    }

    @Test
     void testGetId() {
        DataNode dataNode = new DataNode();
        dataNode.setId(1);
        assertEquals(1, dataNode.getId());
    }

    @Test
     void testGetQuestion() {
        DataNode dataNode = new DataNode();
        dataNode.setQuestion("Test Question");
        assertEquals("Test Question", dataNode.getQuestion());
    }

    @Test
     void testGetResponses() {
        DataNode dataNode = new DataNode();
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        dataNode.setResponses(responses);
        assertEquals(responses, dataNode.getResponses());
    }

    @Test
     void testGetUserId() {
        DataNode dataNode = new DataNode();
        dataNode.setUserId(1);
        assertEquals(1, dataNode.getUserId());
    }

    @Test
     void testGetSessionId() {
        DataNode dataNode = new DataNode();
        dataNode.setSessionId(2);
        assertEquals(2, dataNode.getSessionId());
    }

    @Test
     void testGetSessionPinAndSetSessionPin() {
        DataNode dataNode = new DataNode();
        dataNode.setsessionPin(3);
        assertEquals(3, dataNode.getsessionPin());
    }
}
