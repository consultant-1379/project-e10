package com.backend;

import com.backend.util.ResultConversion;
import com.backend.util.ScriptPython;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScriptPythonTests {
    private static String scriptName;
    private static String result;
    private static String requestId;

    @BeforeAll
    public static void init() {
        scriptName = "generate_graph.py";
        result = "2,3,4,3,2.5,3,2.5,2,4,3";
        requestId = "1234";
    }

    @Test
    void testScriptPythonEmptyConstructor(){
        ScriptPython scr = new ScriptPython();
        assertNotNull(scr);
    }

    @Test
    void testResultConversionParameterisedConstructor(){
        ScriptPython scr = new ScriptPython(scriptName, result, requestId);
        assertNotNull(scr);
        assertEquals(scriptName, scr.getScriptName());
        assertEquals(result, scr.getResults());
        assertEquals(requestId, scr.getRequestId());
    }

    @Test
    void testGetScriptName(){
        ScriptPython scr = new ScriptPython(scriptName, result, requestId);
        scr.setScriptName("test");
        assertEquals("test", scr.getScriptName());
    }

    @Test
    void testGetResult(){
        ScriptPython scr = new ScriptPython(scriptName, result, requestId);
        scr.setResults("test");
        assertEquals("test", scr.getResults());
    }

    @Test
    void testGetRequestId(){
        ScriptPython scr = new ScriptPython(scriptName, result, requestId);
        scr.setRequestId("1");
        assertEquals("1", scr.getRequestId());
    }

    @Test
    void testRunScript_validInputs() throws IOException, JSONException {
        ScriptPython scr = new ScriptPython(scriptName, result, requestId);
        JSONObject j = scr.runScript();
        assertNotNull(j);
        assertTrue(j.getString("img").length() > 256);
    }

    @Test
    void testRunScript_invalidInputs() throws IOException, JSONException {
        ScriptPython scr = new ScriptPython("generate_graph.py",
                "1,2,3,4,5", "2");
        JSONObject j = scr.runScript();
        assertNull(j);
    }

}
