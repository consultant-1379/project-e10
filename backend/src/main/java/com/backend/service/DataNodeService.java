package com.backend.service;

import com.backend.entities.UserNode;
import com.backend.repository.DataNodeRepository;
import com.backend.entities.DataNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataNodeService {

    private final DataNodeRepository dataNodeRepository;

    @Autowired
    public DataNodeService(DataNodeRepository dataNodeRepository) {
        this.dataNodeRepository = dataNodeRepository;
    }

    public DataNode saveDataNode(DataNode dataNode) {
        return dataNodeRepository.save(dataNode);
    }

    public DataNode getDataNodeById(int id) {
        return dataNodeRepository.findById(id).orElse(null);
    }

    public List<DataNode> getAllDataNodes() {
        return dataNodeRepository.findAll();
    }

    public void deleteDataNodeById(int id) {
        dataNodeRepository.deleteById(id);
    }

    public void addList(ArrayList<DataNode> nodes) {
        dataNodeRepository.saveAll(nodes);
    }

    public UserNode generateUserToken() {
        return new UserNode();
    }

    public List<DataNode> getDataNodesBySessionIdAndSessionPin(int sessionId, int sessionPin) {
        List<DataNode> allDataNodes = dataNodeRepository.findAll();
        List<DataNode> filteredDataNodes = new ArrayList<>();

        for (DataNode dataNode : allDataNodes) {
            if (dataNode.getSessionId() == (sessionId) && dataNode.getsessionPin() == (sessionPin)) {
                filteredDataNodes.add(dataNode);
            }
        }
        return filteredDataNodes;
    }

    public List<DataNode> getDataNodesBySessionId(int sessionId) {
        List<DataNode> allDataNodes = dataNodeRepository.findAll();
        List<DataNode> filteredDataNodes = new ArrayList<>();

        for (DataNode dataNode : allDataNodes) {
            if (dataNode.getSessionId() == (sessionId)) {
                filteredDataNodes.add(dataNode);
            }
        }
        return filteredDataNodes;
    }
}

