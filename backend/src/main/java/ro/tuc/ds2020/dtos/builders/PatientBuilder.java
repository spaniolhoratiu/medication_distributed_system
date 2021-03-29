package ro.tuc.ds2020.dtos.builders;

import org.hibernate.Hibernate;
import ro.tuc.ds2020.dtos.*;
import ro.tuc.ds2020.entities.*;

import java.util.ArrayList;
import java.util.List;

public class PatientBuilder {

    private PatientBuilder() {
    }

    public static PatientDTO toPatientDTO(Patient patient) {
        Hibernate.initialize(patient);
        Hibernate.initialize(patient.getCaregivers());
        Hibernate.initialize(patient.getMedicationPlans());

        List<CaregiverDTO> caregiverDTOS = new ArrayList<>();
        if(patient.getCaregivers() != null)
        {
            for (Caregiver caregiver : patient.getCaregivers())
            {
                UserDTO userDTO = UserBuilder.toUserDTO(caregiver.getUser());
                CaregiverDTO caregiverDTO = new CaregiverDTO(caregiver.getId(), caregiver.getName(), caregiver.getBirthDate(), caregiver.getGender(), caregiver.getAddress(), userDTO);
                //// CaregiverBuilder.toCaregiverDTO(caregiver);
                caregiverDTOS.add(caregiverDTO);
            }
        }

        List<MedicationPlanDTO> medicationPlanDTOS = new ArrayList<>();
        if(patient.getMedicationPlans() != null)
        {
            for(MedicationPlan medicationPlan : patient.getMedicationPlans())
            {
                MedicationPlanDTO medicationPlanDTO = new MedicationPlanDTO(medicationPlan.getId(), medicationPlan.getName());
                        //MedicationPlanBuilder.toMedicationPlanDTO(medicationPlan);
                medicationPlanDTOS.add(medicationPlanDTO);
            }
        }

        UserDTO userDTO = UserBuilder.toUserDTO(patient.getUser());

        return new PatientDTO(patient.getId(), patient.getName(), patient.getBirthDate(), patient.getGender(), patient.getAddress(), patient.getMedicalRecord(), userDTO, caregiverDTOS, medicationPlanDTOS);
    }

    public static Patient toEntity(PatientDTO patientDTO) {

        User userEntity = UserBuilder.toEntity(patientDTO.getUserDTO());

        List<Caregiver> caregiverEntities = new ArrayList<>();
        if(patientDTO.getCaregiversDTO() != null) {
            for (CaregiverDTO c : patientDTO.getCaregiversDTO()) {
                caregiverEntities.add(CaregiverBuilder.toEntity(c));
            }
        }else
        {
            patientDTO.setCaregiversDTO(new ArrayList<>());
        }

        List<MedicationPlan> medicationPlanEntities = new ArrayList<>();
        if(patientDTO.getMedicationPlansDTO() != null)
        {
            for(MedicationPlanDTO m : patientDTO.getMedicationPlansDTO()) {
                medicationPlanEntities.add(MedicationPlanBuilder.toEntity(m));
            }
        }else
        {
            patientDTO.setMedicationPlansDTO(new ArrayList<>());
        }

        return new Patient(patientDTO.getName(),
                patientDTO.getBirthDate(),
                patientDTO.getGender(),
                patientDTO.getAddress(),
                patientDTO.getMedicalRecord(),
                userEntity,
                caregiverEntities,
                medicationPlanEntities
        );
    }
}
