package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class ActivityCreateRequest {

    private UUID patient_id;
    private String activity;
    private long start;
    private long end;

    public ActivityCreateRequest(UUID patient_id, String activity, long start, long end) {
        this.patient_id = patient_id;
        this.activity = activity;
        this.start = start;
        this.end = end;
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
}
