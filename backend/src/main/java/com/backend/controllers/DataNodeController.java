package com.backend.controllers;
import com.backend.entities.UserNode;
import com.backend.service.DataNodeService;
import com.backend.entities.DataNode;
import com.backend.util.ResultConversion;
import com.backend.util.ScriptPython;
import org.jruby.util.RegexpSupport;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/form_results")
public class DataNodeController {

    @Autowired
    private DataNodeService dataNodeService;

    @PostMapping
    public DataNode saveDataNode(@RequestBody DataNode dataNode) {
        return dataNodeService.saveDataNode(dataNode);
    }

    @GetMapping("/{id}")
    public DataNode getDataNode(@PathVariable int id) {
        return dataNodeService.
                getDataNodeById(id);
    }

    @GetMapping
    public List<DataNode> getAllDataNodes() {
        return dataNodeService.getAllDataNodes();
    }

    @GetMapping("/GenerateToken")
    public UserNode generateUserToken() {
        return dataNodeService.generateUserToken();
    }

    @DeleteMapping("/{id}")
    public void deleteDataNode(@PathVariable int id) {
        dataNodeService.deleteDataNodeById(id);
    }

    @PostMapping("/list")
    public void addDataNodelist(@RequestBody ArrayList<DataNode> nodes) {
        dataNodeService.addList(nodes);
    }

    @GetMapping("/by-session")
    public ResponseEntity<List<DataNode>> getDataNodesBySessionIdAndSessionPin(
            @RequestParam int sessionId,
            @RequestParam int sessionPin) {

        List<DataNode> dataNodes = dataNodeService.getDataNodesBySessionIdAndSessionPin(sessionId, sessionPin);

        if (dataNodes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(dataNodes);
        }
    }

    @GetMapping("/session")
    public List<DataNode> getDataNodesBySessionId(@RequestParam int sessionId){
        return dataNodeService.getDataNodesBySessionId(sessionId);
    }

    @GetMapping("/graph")
    public ResponseEntity<String> getSurveyResultsGraph(@RequestParam int sessionId){
        List<DataNode> sessionNodes = getDataNodesBySessionId(sessionId);
        ResultConversion conv = new ResultConversion(sessionNodes);
        String results = conv.calculateResults();

        ScriptPython py = new ScriptPython("generate_graph.py",
                results, String.valueOf(sessionId));
        try {
            JSONObject obj =  py.runScript();
            if (obj == null|| obj.length() == 0){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(obj.getString("img"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


