package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class PatientCreateRequest {
    private String name;
    private String address;
    private String birthDate;
    private String gender;
    private String medicalRecord;
    private UUID userDTOId;

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public UUID getUserDTOId() {
        return userDTOId;
    }
}
