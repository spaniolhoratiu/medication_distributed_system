package ro.tuc.ds2020.dtos;

import java.util.*;

public class CaregiverDTO {

    private UUID id;
    private String name;
    private Date birthDate;
    private String gender;
    private String address;
    private UserDTO userDTO;
    private List<PatientDTO> patientsDTO;

    public CaregiverDTO() {
    }

    public CaregiverDTO(UUID id, String name, Date birthDate, String gender, String address, UserDTO userDTO, List<PatientDTO> patientsDTO) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.userDTO = userDTO;
        this.patientsDTO = patientsDTO;
    }

    public CaregiverDTO(String name, Date birthDate, String gender, String address, UserDTO userDTO) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.userDTO = userDTO;
        this.patientsDTO = new ArrayList<>();
    }

    public CaregiverDTO(UUID id, String name, Date birthDate, String gender, String address, UserDTO userDTO) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.userDTO = userDTO;
        this.patientsDTO = new ArrayList<>();
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<PatientDTO> getPatientDTOs() {
        return patientsDTO;
    }

    public void setPatientDTOs(List<PatientDTO> patientsDTO) {
        this.patientsDTO = patientsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaregiverDTO that = (CaregiverDTO) o;
        return id.equals(that.id) &&
                birthDate.equals(that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate);
    }
}
