package com.backend.entities;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "json_data")
public class DataNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    private int userId;

    @Column(name = "session_id")
    private int sessionId;

    @Column(name = "session_pin")
    private int sessionPin;

    @ElementCollection
    @CollectionTable(name = "survery_responses")
    @MapKeyColumn(name = "response_key")
    @Column(name = "response_value", columnDefinition = "Text")
    private Map<String, String> responses;

    public DataNode() {
    }

    public DataNode(String question, Map<String, String> responses, UserNode userNode) {
        this.question = question;
        this.responses = responses;
        this.userId = userNode.getUserId();
        this.sessionId = userNode.getSessionId();
        this.sessionPin = userNode.getSessionPin();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setResponses(Map<String, String> responses) {
        this.responses = responses;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, String> getResponses() {
        return responses;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    public int getsessionPin() {
        return sessionPin;
    }
    public void setsessionPin(int sessionPin) {
        this.sessionPin = sessionPin;
    }

}
