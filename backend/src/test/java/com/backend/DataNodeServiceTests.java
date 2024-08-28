package com.backend;

import com.backend.entities.DataNode;
import com.backend.entities.UserNode;
import com.backend.repository.DataNodeRepository;
import com.backend.service.DataNodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class DataNodeServiceTests {

    @Mock
    private DataNodeRepository dataNodeRepository;

    @InjectMocks
    private DataNodeService dataNodeService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testSaveDataNode() {
        DataNode testDataNode = new DataNode("Question 1", Map.of("1", "Response 1"), new UserNode());

        when(dataNodeRepository.save(testDataNode)).thenReturn(testDataNode);

        DataNode savedDataNode = dataNodeService.saveDataNode(testDataNode);

        assertNotNull(savedDataNode);
        assertEquals("Question 1", savedDataNode.getQuestion());
        assertEquals("Response 1", savedDataNode.getResponses().get("1"));
    }

    @Test
     void testGetDataNodeById() {
        int dataNodeId = 1;
        DataNode testDataNode = new DataNode("Question 1", Map.of("1", "Response 1"), new UserNode());

        when(dataNodeRepository.findById(dataNodeId)).thenReturn(java.util.Optional.ofNullable(testDataNode));

        DataNode retrievedDataNode = dataNodeService.getDataNodeById(dataNodeId);

        assertNotNull(retrievedDataNode);
        assertEquals("Question 1", retrievedDataNode.getQuestion());
        assertEquals("Response 1", retrievedDataNode.getResponses().get("1"));
    }

    @Test
     void testGetAllDataNodes() {
        DataNode testDataNode1 = new DataNode("Question 1", Map.of("1", "Response 1"), new UserNode());
        DataNode testDataNode2 = new DataNode("Question 2", Map.of("2", "Response 2"), new UserNode());
        List<DataNode> testDataNodes = new ArrayList<>();
        testDataNodes.add(testDataNode1);
        testDataNodes.add(testDataNode2);

        when(dataNodeRepository.findAll()).thenReturn(testDataNodes);

        List<DataNode> allDataNodes = dataNodeService.getAllDataNodes();

        assertNotNull(allDataNodes);
        assertEquals(2, allDataNodes.size());
    }


    @Test
     void testDeleteDataNodeById() {
        int dataNodeId = 1;
        DataNode testDataNode = new DataNode("Question 1", Map.of("1", "Response 1"), new UserNode());

        when(dataNodeRepository.findById(dataNodeId)).thenReturn(java.util.Optional.ofNullable(testDataNode));

        dataNodeService.deleteDataNodeById(dataNodeId);

        verify(dataNodeRepository, times(1)).deleteById(dataNodeId);
    }

    @Test
     void testAddList() {
        ArrayList<DataNode> testDataNodes = new ArrayList<>();
        testDataNodes.add(new DataNode("Question 1", Map.of("1", "Response 1"), new UserNode()));
        testDataNodes.add(new DataNode("Question 2", Map.of("2", "Response 2"), new UserNode()));

        dataNodeService.addList(testDataNodes);

        verify(dataNodeRepository, times(1)).saveAll(testDataNodes);
    }

    @Test
     void testGenerateUserToken() {
        UserNode userNode = dataNodeService.generateUserToken();
        assertNotNull(userNode);
    }

     @Test
     void testGetDataNodesBySessionIdAndSessionPin() {
         int sessionId = 1;
         int sessionPin = 12345;

         List<DataNode> testDataNodes = new ArrayList<>();
         UserNode user1 = new UserNode();
         user1.setSessionPin(sessionPin);
         user1.setSessionId(sessionId);
         testDataNodes.add(new DataNode("Question 1", Map.of("1", "Response 1"), user1));

         UserNode user2 = new UserNode();
         user2.setSessionPin(sessionPin);
         user2.setSessionId(2); // Different session ID
         testDataNodes.add(new DataNode("Question 2", Map.of("2", "Response 2"), user2));
         when(dataNodeRepository.findAll()).thenReturn(testDataNodes);

         List<DataNode> result = dataNodeService.getDataNodesBySessionIdAndSessionPin(sessionId, sessionPin);
         // Assert that the result contains only the nodes with the correct session pin
         assertEquals(1, result.size());
         assertEquals("Question 1", result.get(0).getQuestion()); // Verify a specific node
     }
}

