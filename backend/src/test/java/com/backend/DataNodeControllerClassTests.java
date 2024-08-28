package com.backend;

import com.backend.controllers.DataNodeController;
import com.backend.entities.DataNode;
import com.backend.entities.UserNode;
import com.backend.service.DataNodeService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataNodeControllerClassTests {

    private static List<DataNode> sampleNodes;
    private static DataNode sampleNode;
    private static int sampleSessionId;
    private static int sampleSessionPIN;

    @InjectMocks
    private DataNodeController dataNodeController;

    @Mock
    private DataNodeService dataNodeService;

    @BeforeAll
    public static void init() {
        sampleNodes = new ArrayList<>();
        sampleSessionId = 1234;
        sampleSessionPIN = 1234;

        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
        responses.put("2", "Our development teams focus on achieving small, defined objectives quickly and then moving immediately to the next one.");
        responses.put("3", "A lot of up-front planning goes into documenting each step of a project before it even begins.");

        Map<String, String> responses2 = new HashMap<>();
        responses2.put("2", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
        responses2.put("4", "A lot of up-front planning goes into documenting each step of a project before it even begins.");

        Map<String, String> responses3 = new HashMap<>();
        responses2.put("0", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");

        UserNode user = new UserNode();
        user.setSessionId(sampleSessionId);
        user.setSessionPin(sampleSessionPIN);
        DataNode node1 = new DataNode("test", responses, user);
        sampleNode = node1;
        sampleNodes.add(node1);
        DataNode node2 = new DataNode("test", responses2, user);
        sampleNodes.add(node2);
        DataNode node3 = new DataNode("test", responses2, user);
        sampleNodes.add(node3);
        DataNode node4 = new DataNode("test", responses2, user);
        sampleNodes.add(node4);
        DataNode node5 = new DataNode("test", responses, user);
        sampleNodes.add(node5);
        DataNode node6 = new DataNode("test", responses, user);
        sampleNodes.add(node6);
        DataNode node7 = new DataNode("test", responses, user);
        sampleNodes.add(node7);
        DataNode node8 = new DataNode("test", responses2, user);
        sampleNodes.add(node8);
        DataNode node9 = new DataNode("test", responses, user);
        sampleNodes.add(node9);
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDataNode() {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses, user);

        when(dataNodeService.saveDataNode(any(DataNode.class))).thenReturn(dataNode);
        DataNode savedNode = dataNodeController.saveDataNode(dataNode);

        assertEquals(dataNode, savedNode);
    }

    @Test
    public void testGetDataNode() {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses, user);
        dataNode.setId(1);

        when(dataNodeService.getDataNodeById(1)).thenReturn(dataNode);
        when(dataNodeService.getDataNodeById(2)).thenReturn(null); // Non-existing ID

        DataNode retrievedNode1 = dataNodeController.getDataNode(1);
        DataNode retrievedNode2 = dataNodeController.getDataNode(2);
        assertEquals(dataNode, retrievedNode1);
        assertNull(retrievedNode2);
    }

    @Test
    public void testGetAllDataNodes() {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        UserNode user2 = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses, user);
        DataNode dataNode2 = new DataNode("Question 2", responses, user2);
        ArrayList<DataNode> testDataNodes = new ArrayList<>();
        testDataNodes.add(dataNode);
        testDataNodes.add(dataNode2);

        when(dataNodeService.getAllDataNodes()).thenReturn(testDataNodes);

        List<DataNode> allNodes = dataNodeController.getAllDataNodes();

        assertEquals(testDataNodes, allNodes);
    }

    @Test
    public void testGenerateUserToken() {
        UserNode testUserNode = new UserNode();

        when(dataNodeService.generateUserToken()).thenReturn(testUserNode);

        UserNode generatedToken = dataNodeController.generateUserToken();

        assertEquals(testUserNode, generatedToken);
    }

    @Test
    public void testDeleteDataNode() {
        doNothing().when(dataNodeService).deleteDataNodeById(1); // Successful deletion
        doThrow(new RuntimeException("Node not found")).when(dataNodeService).deleteDataNodeById(2); // Deletion fails

        dataNodeController.deleteDataNode(1);
        try {
            dataNodeController.deleteDataNode(2);
            fail("Expected RuntimeException for unsuccessful deletion");
        } catch (RuntimeException e) {
            assertEquals("Node not found", e.getMessage());
        }
    }

    @Test
    public void testAddDataNodelist() {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        UserNode user2 = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses, user);
        DataNode dataNode2 = new DataNode("Question 2", responses, user2);
        ArrayList<DataNode> testDataNodes = new ArrayList<>();
        testDataNodes.add(dataNode);
        testDataNodes.add(dataNode2);

        // Verify that the DataNodeService's addList method was called with the correct list
        dataNodeController.addDataNodelist(testDataNodes);
        verify(dataNodeService).addList(testDataNodes);
    }

    @Test
    public void testGetDataNodesBySessionIdAndSessionPin() {
        int sessionId = 1;
        int sessionPin = 12345;

        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        UserNode user2 = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses, user);
        DataNode dataNode2 = new DataNode("Question 2", responses, user2);
        dataNode2.setsessionPin(sessionPin);
        dataNode2.setSessionId(sessionId);
        ArrayList<DataNode> testDataNodes = new ArrayList<>();
        testDataNodes.add(dataNode);
        testDataNodes.add(dataNode2);

        when(dataNodeService.getDataNodesBySessionIdAndSessionPin(sessionId, sessionPin)).thenReturn(testDataNodes);
        when(dataNodeService.getDataNodesBySessionIdAndSessionPin(3, 67890)).thenReturn(new ArrayList<>()); // Empty list

        ResponseEntity<List<DataNode>> response1 = dataNodeController.getDataNodesBySessionIdAndSessionPin(sessionId, sessionPin);
        ResponseEntity<List<DataNode>> response2 = dataNodeController.getDataNodesBySessionIdAndSessionPin(3, 67890);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(testDataNodes, response1.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void testGetDataNodesBySessionId() {
        int sessionId = 3;

        when(dataNodeService.getDataNodesBySessionId(sampleSessionId)).thenReturn(sampleNodes);
        when(dataNodeService.getDataNodesBySessionId(sessionId)).thenReturn(new ArrayList<>()); // Empty list

        List<DataNode> response1 = dataNodeController.getDataNodesBySessionId(sampleSessionId);
        List<DataNode> response2 = dataNodeController.getDataNodesBySessionId(sessionId);

        assertTrue(response1.contains(sampleNode));
        assertFalse(response2.contains(sampleNode));
    }

    @Test
    public void testGetSurveyResultsGraph() {
        System.out.println(sampleNodes);
        when(dataNodeController.getDataNodesBySessionId(sampleSessionId)).thenReturn(sampleNodes);
        when(dataNodeController.getDataNodesBySessionId(1)).thenReturn(new ArrayList<>()); // Empty list

        System.out.println();

        ResponseEntity<String> response1 = dataNodeController.getSurveyResultsGraph(sampleSessionId);
        ResponseEntity<String> response2 = dataNodeController.getSurveyResultsGraph(1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertTrue(Objects.requireNonNull(response1.getBody()).length() > 256);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }
}
