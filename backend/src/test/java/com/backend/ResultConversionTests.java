package com.backend;

import com.backend.entities.DataNode;
import com.backend.entities.UserNode;
import com.backend.util.ResultConversion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultConversionTests {

    private static List<DataNode> sampleNodes;
    private static List<DataNode> failSetNodes;

    @BeforeAll
    public static void init() {
        sampleNodes = new ArrayList<>();
        failSetNodes = new ArrayList<>();
        int sampleSessionId = 1234;

        Map<String, String> responses = new HashMap<>();
        responses.put("1", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
        responses.put("2", "Our development teams focus on achieving small, defined objectives quickly and then moving immediately to the next one.");
        responses.put("3", "A lot of up-front planning goes into documenting each step of a project before it even begins.");

        Map<String, String> responses2 = new HashMap<>();
        responses2.put("2", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");
        responses2.put("4", "A lot of up-front planning goes into documenting each step of a project before it even begins.");

        Map<String, String> responses3 = new HashMap<>();
        responses3.put("0", "Project managers coordinate between all the different teams working on the same project, and the teams have highly specialized responsibilities.");

        UserNode user = new UserNode();
        user.setSessionId(sampleSessionId);
        DataNode node1 = new DataNode("test", responses, user);
        sampleNodes.add(node1);
        failSetNodes.add(node1);
        DataNode node2 = new DataNode("test", responses2, user);
        sampleNodes.add(node2);
        DataNode node3 = new DataNode("test", responses2, user);
        sampleNodes.add(node3);
        DataNode node4 = new DataNode("test", responses2, user);
        sampleNodes.add(node4);
        failSetNodes.add(node4);
        DataNode node5 = new DataNode("test", responses, user);
        sampleNodes.add(node5);
        DataNode node6 = new DataNode("test", responses3, user);
        sampleNodes.add(node6);
        DataNode node7 = new DataNode("test", responses, user);
        sampleNodes.add(node7);
        failSetNodes.add(node7);
        DataNode node8 = new DataNode("test", responses2, user);
        sampleNodes.add(node8);
        DataNode node9 = new DataNode("test", responses3, user);
        sampleNodes.add(node9);
    }

    @Test
    void testResultConversionConstructor(){
        ResultConversion conv = new ResultConversion(sampleNodes);
        assertNotNull(conv);
        assertEquals(sampleNodes, conv.getNodes());
        assertEquals("", conv.getResult());
    }

    @Test
    void testGetNodes(){
        ResultConversion conv = new ResultConversion(sampleNodes);
        assertEquals(sampleNodes, conv.getNodes());
    }

    @Test
    void testGetResult(){
        ResultConversion conv = new ResultConversion(sampleNodes);
        conv.setResult("test");
        assertEquals("test", conv.getResult());
    }

    @Test
    void testCalculateResults_insufficientDataNodes(){
        ResultConversion conv = new ResultConversion(failSetNodes);
        assertEquals("", conv.calculateResults());
    }

    @Test
    void testCalculateResults_returnCorrectResults(){
        ResultConversion conv = new ResultConversion(sampleNodes);
        assertEquals("2.5,3,3,3,2.5,4,2.5,3,4", conv.calculateResults());
    }
}
