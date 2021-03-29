package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.User;

import java.util.Date;
import java.util.Objects;
import java.util.List;
import java.util.UUID;

public class PatientDTO {

    private UUID id;
    private String name;
    private Date birthDate;
    private String gender;
    private String address;
    private String medicalRecord;
    private UserDTO userDTO;
    private List<CaregiverDTO> caregiversDTO;
    private List<MedicationPlanDTO> medicationPlansDTO;

    public PatientDTO() {
    }

    public PatientDTO(UUID id, String name, Date birthDate, String gender, String address, String medicalRecord, UserDTO userDTO, List<CaregiverDTO> caregiversDTO, List<MedicationPlanDTO> medicationPlansDTO) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalRecord = medicalRecord;
        this.userDTO = userDTO;
        this.caregiversDTO = caregiversDTO;
        this.medicationPlansDTO = medicationPlansDTO;
    }

    public PatientDTO(UUID id, String name, Date birthDate, String gender, String address, String medicalRecord, UserDTO userDTO) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalRecord = medicalRecord;
        this.userDTO = userDTO;
    }

    public PatientDTO(String name, Date birthDate, String gender, String address, String medicalRecord, UserDTO userDTO) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalRecord = medicalRecord;
        this.userDTO = userDTO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<CaregiverDTO> getCaregiversDTO() {
        return caregiversDTO;
    }

    public void setCaregiversDTO(List<CaregiverDTO> caregiversDTO) {
        this.caregiversDTO = caregiversDTO;
    }

    public List<MedicationPlanDTO> getMedicationPlansDTO() {
        return medicationPlansDTO;
    }

    public void setMedicationPlansDTO(List<MedicationPlanDTO> medicationPlansDTO) {
        this.medicationPlansDTO = medicationPlansDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDTO that = (PatientDTO) o;
        return id.equals(that.id) &&
                birthDate.equals(that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate);
    }
}
