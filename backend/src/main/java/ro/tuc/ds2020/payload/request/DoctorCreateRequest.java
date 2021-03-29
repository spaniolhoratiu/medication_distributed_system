package ro.tuc.ds2020.payload.request;

import ro.tuc.ds2020.dtos.UserDTO;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public class DoctorCreateRequest {

    private String name;
    private String birthDate;
    private String gender;
    private String address;
    private UUID userDTOId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public UUID getUserDTOId() {
        return userDTOId;
    }

    public void setUserDTOId(UUID userDTOId) {
        this.userDTOId = userDTOId;
    }
}
