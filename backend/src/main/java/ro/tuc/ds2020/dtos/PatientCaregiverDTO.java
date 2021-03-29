package ro.tuc.ds2020.dtos;

import java.util.Date;
import java.util.UUID;

public class PatientCaregiverDTO {

    private UUID patientId;
    private String patientName;
    private Date patientBirthDate;
    private String patientGender;
    private String patientAddress;
    private String patientMedicalRecord;
    private UUID patientUserId;

    private UUID caregiverId;
    private String caregiverName;
    private Date caregiverBirthDate;
    private String caregiverGender;
    private UUID caregiverUserId;

    public PatientCaregiverDTO(PatientDTO patientDTO, CaregiverDTO caregiverDTO)
    {
        this.patientId = patientDTO.getId();
        this.patientName = patientDTO.getName();
        this.patientBirthDate = patientDTO.getBirthDate();
        this.patientGender = patientDTO.getGender();
        this.patientAddress = patientDTO.getAddress();
        this.patientMedicalRecord = patientDTO.getMedicalRecord();
        this.patientUserId = patientDTO.getUserDTO().getId();

        this.caregiverId = caregiverDTO.getId();
        this.caregiverName = caregiverDTO.getName();
        this.caregiverBirthDate = caregiverDTO.getBirthDate();
        this.caregiverGender = caregiverDTO.getGender();
        this.caregiverUserId = caregiverDTO.getUserDTO().getId();
    }


    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(Date patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientMedicalRecord() {
        return patientMedicalRecord;
    }

    public void setPatientMedicalRecord(String patientMedicalRecord) {
        this.patientMedicalRecord = patientMedicalRecord;
    }

    public UUID getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(UUID caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public Date getCaregiverBirthDate() {
        return caregiverBirthDate;
    }

    public void setCaregiverBirthDate(Date caregiverBirthDate) {
        this.caregiverBirthDate = caregiverBirthDate;
    }

    public String getCaregiverGender() {
        return caregiverGender;
    }

    public void setCaregiverGender(String caregiverGender) {
        this.caregiverGender = caregiverGender;
    }

    public UUID getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(UUID patientUserId) {
        this.patientUserId = patientUserId;
    }

    public UUID getCaregiverUserId() {
        return caregiverUserId;
    }

    public void setCaregiverUserId(UUID caregiverUserId) {
        this.caregiverUserId = caregiverUserId;
    }
}
