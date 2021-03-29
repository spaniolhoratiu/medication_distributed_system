package ro.tuc.ds2020.dtos.builders;

import org.hibernate.Hibernate;
import ro.tuc.ds2020.dtos.DrugDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.dtos.PatientDTO;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.MedicationPlanDrug;
import ro.tuc.ds2020.entities.Patient;

import java.util.ArrayList;
import java.util.List;

public class MedicationPlanBuilder {

    private MedicationPlanBuilder() {
    }

    public static MedicationPlanDTO toMedicationPlanDTO(MedicationPlan medicationPlan) {

        PatientDTO patientDTO = PatientBuilder.toPatientDTO(medicationPlan.getPatient());

        List<MedicationPlanDrugDTO> medicationPlanDrugDTOS = new ArrayList<>();
        if(medicationPlan.getDrugs() != null)
        {
            for(MedicationPlanDrug medicationPlanDrug : medicationPlan.getDrugs())
            {
                DrugDTO drugDTO = new DrugDTO(
                        medicationPlanDrug.getDrug().getId(),
                        medicationPlanDrug.getDrug().getName(),
                        medicationPlanDrug.getDrug().getSideEffects(),
                        medicationPlanDrug.getDrug().getDosage()
                );

                //MedicationPlanDrugDTO medicationPlanDrugDTO = MedicationPlanDrugBuilder.toMedicationPlanDrugDTO(medicationPlanDrug);
                MedicationPlanDrugDTO medicationPlanDrugDTO = new MedicationPlanDrugDTO(
                            medicationPlanDrug.getId(),
                            medicationPlanDrug.getStartDate(),
                            medicationPlanDrug.getEndDate(),
                            medicationPlanDrug.getIntakeInterval(),
                            drugDTO
                        );

                medicationPlanDrugDTOS.add(medicationPlanDrugDTO);
            }
        }

        return new MedicationPlanDTO(medicationPlan.getId(), medicationPlan.getName(), patientDTO, medicationPlanDrugDTOS);
    }

    public static MedicationPlan toEntity(MedicationPlanDTO medicationPlanDTO) {

        Patient patient = PatientBuilder.toEntity(medicationPlanDTO.getPatientDTO());

        List<MedicationPlanDrug> medicationPlanDrugs = new ArrayList<>();
        for(MedicationPlanDrugDTO m : medicationPlanDTO.getMedicationPlanDrugsDTO())
        {
            medicationPlanDrugs.add(MedicationPlanDrugBuilder.toEntity(m));
        }

        return new MedicationPlan(medicationPlanDTO.getId(),
                medicationPlanDTO.getName(),
                patient,
                medicationPlanDrugs);
    }
}
