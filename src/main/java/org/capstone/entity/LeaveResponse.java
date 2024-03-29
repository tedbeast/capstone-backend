package org.capstone.entity;

import org.springframework.http.HttpStatus;

public class LeaveResponse {
    private HttpStatus statusCode;
    private String statusMessage;
    private Leave updatedLeave;

    public LeaveResponse(HttpStatus statusCode, String statusMessage, Leave updatedLeave) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.updatedLeave = updatedLeave;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Leave getUpdatedLeave() {
        return updatedLeave;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setUpdatedLeave(Leave updatedLeave) {
        this.updatedLeave = updatedLeave;
    }
}
