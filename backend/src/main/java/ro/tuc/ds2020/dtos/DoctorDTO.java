package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.User;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class DoctorDTO {
    private UUID id;
    private String name;
    private Date birthDate;
    private String gender;
    private String address;
    private UserDTO userDTO;

    public DoctorDTO() {
    }

    public DoctorDTO(UUID id, String name, Date birthDate, String gender, String address, UserDTO userDTO) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.userDTO = userDTO;
    }

    public DoctorDTO(String name, Date birthDate, String gender, String address, UserDTO userDTO) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDTO doctorDTO = (DoctorDTO) o;
        return id.equals(doctorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
