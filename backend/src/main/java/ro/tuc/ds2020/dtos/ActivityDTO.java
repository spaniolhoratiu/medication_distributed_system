package ro.tuc.ds2020.dtos;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityDTO implements Serializable {
    private UUID patient_id;
    private String activity;
    private long start;
    private long end;

    public ActivityDTO(@JsonProperty("patient_id") UUID patient_id,
                       @JsonProperty("activity") String activity,
                       @JsonProperty("start") long start,
                       @JsonProperty("end") long end) {

        this.patient_id = patient_id;
        this.activity = activity;
        this.start = start;
        this.end = end;
    }

    public ActivityDTO() {
    }

    public UUID getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(UUID patient_id) {
        this.patient_id = patient_id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Activity from RabbitMQ. " + this.activity;
    }
}