package com.backend.entities;

import jakarta.persistence.*;

import java.security.SecureRandom;

@Entity
@Table(name = "User_data")
public class UserNode {
    SecureRandom secureRandom = new SecureRandom();

    @Id
    private int userId;
    private int sessionId;
    private int sessionPin;

    public UserNode() {
        this.sessionId = generateUniqueHumanReadableId();
        this.userId = generateUniqueHumanReadableId();
        this.sessionPin = generateUniqueHumanReadableId();
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

    public int getSessionPin() {
        return sessionPin;
    }

    public void setSessionPin(int sessionPin) {
        this.sessionPin = sessionPin;
    }

    private int generateUniqueHumanReadableId() {
        int val = secureRandom.nextInt();
        if (val < 0) {
            val = val * -1;
        }
        return val;
    }

}

