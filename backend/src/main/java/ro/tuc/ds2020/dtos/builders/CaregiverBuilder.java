package ro.tuc.ds2020.dtos.builders;

import org.hibernate.Hibernate;
import ro.tuc.ds2020.dtos.CaregiverDTO;
import ro.tuc.ds2020.dtos.PatientDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.entities.User;

import java.util.ArrayList;
import java.util.List;

public class CaregiverBuilder {

    private CaregiverBuilder() {
    }

    public static CaregiverDTO toCaregiverDTO(Caregiver caregiver) {

        List<PatientDTO> patientsDTO = new ArrayList<>();
        if(caregiver.getPatients() != null)
        {
            for(Patient p : caregiver.getPatients())
            {
                UserDTO userDTO = UserBuilder.toUserDTO(p.getUser());
                PatientDTO patientDTO = new PatientDTO(
                        p.getId(),
                        p.getName(),
                        p.getBirthDate(),
                        p.getGender(),
                        p.getAddress(),
                        p.getMedicalRecord(),
                        userDTO);
                patientsDTO.add(patientDTO);
            }
        }

        UserDTO userDTO = UserBuilder.toUserDTO(caregiver.getUser());

        return new CaregiverDTO(caregiver.getId(), caregiver.getName(), caregiver.getBirthDate(), caregiver.getGender(), caregiver.getAddress(), userDTO, patientsDTO);
    }

    public static Caregiver toEntity(CaregiverDTO caregiverDTO) {
        User userEntity = UserBuilder.toEntity(caregiverDTO.getUserDTO());

        List<Patient> patients = new ArrayList<>();
        if(caregiverDTO.getPatientDTOs() != null)
        {
            for(PatientDTO p : caregiverDTO.getPatientDTOs())
            {
                patients.add(PatientBuilder.toEntity(p));
            }
        }

        return new Caregiver(caregiverDTO.getName(),
                caregiverDTO.getBirthDate(),
                caregiverDTO.getGender(),
                caregiverDTO.getAddress(),
                userEntity,
                patients);
    }
}
