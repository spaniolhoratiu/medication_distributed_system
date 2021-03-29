package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class PatientUpdateRequest {
    private UUID selectedId;
    private String name;
    private String birthDate;
    private String gender;
    private String address;
    private String medicalRecord;


    public UUID getSelectedId() {
        return selectedId;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }
}
