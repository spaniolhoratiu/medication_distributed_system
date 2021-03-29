package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class PatientCaregiverRequest {
    private UUID patientId;
    private UUID caregiverId;

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getCaregiverId() {
        return caregiverId;
    }
}
