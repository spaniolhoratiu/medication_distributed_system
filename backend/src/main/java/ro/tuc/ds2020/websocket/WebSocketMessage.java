package ro.tuc.ds2020.websocket;

import java.io.Serializable;
import java.util.UUID;

public class WebSocketMessage {
    private UUID patientId;
    private String patientName;
    private String activity;
    private String message;

    public WebSocketMessage(UUID patientId, String patientName, String activity, String message) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.activity = activity;
        this.message = message;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}