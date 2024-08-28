package com.backend;

import com.backend.entities.DataNode;
import com.backend.entities.UserNode;
import com.backend.repository.DataNodeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DataNodeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataNodeRepository dataNodeRepository;

    @Test
    public void testSaveDataNode() throws Exception {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses,user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/form_results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dataNode)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        DataNode savedDataNode = dataNodeRepository.findAll().get(0);
        assertEquals(user.getSessionId(),savedDataNode.getSessionId());
        assertEquals(user.getSessionPin(),savedDataNode.getsessionPin());
        assertEquals("Question 1", savedDataNode.getQuestion());
        assertEquals(responses, savedDataNode.getResponses());
    }

    @Test
    public void testGetDataNode() throws Exception {
        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Response 1");
        responses.put("2", "Response 2");
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("Question 1", responses,user);
        DataNode savedDataNode = dataNodeRepository.save(dataNode);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/form_results/{id}", savedDataNode.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedDataNode.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").value("Question 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responses").value(responses));
    }

    @Test
    public void testGetAllDataNodes() throws Exception {
        UserNode user = new UserNode();
        dataNodeRepository.save(new DataNode("Question 1", new HashMap<>(),user));
        dataNodeRepository.save(new DataNode("Question 2", new HashMap<>(),user));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/form_results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value("Question 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].question").value("Question 2"));
    }

    @Test
    public void testDeleteDataNode() throws Exception {
        UserNode user = new UserNode();
        DataNode dataNode = new DataNode("To Be Deleted", new HashMap<>(),user);
        DataNode savedDataNode = dataNodeRepository.save(dataNode);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/form_results/{id}", savedDataNode.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Ensure the deleted DataNode no longer exists in the repository
        DataNode deletedDataNode = dataNodeRepository.findById(savedDataNode.getId()).orElse(null);
        assertNull(deletedDataNode);
    }

    @Test
    public void testGenerateTokenEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/form_results/GenerateToken"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }




    // Utility method to convert an object to JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
