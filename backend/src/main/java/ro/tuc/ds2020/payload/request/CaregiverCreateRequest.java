package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class CaregiverCreateRequest {
    private String name;
    private String birthDate;
    private String gender;
    private String address;
    private UUID userDTOId;

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

    public UUID getUserDTOId() {
        return userDTOId;
    }
}
