package ro.tuc.ds2020.dtos.builders;

import org.hibernate.Hibernate;
import ro.tuc.ds2020.dtos.DrugDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.entities.MedicationPlanDrug;

import java.util.ArrayList;
import java.util.List;

public class DrugBuilder {

    private DrugBuilder() {
    }

    public static DrugDTO toDrugDTO(Drug drug) {

        List<MedicationPlanDrugDTO> medicationPlanDrugsDTO = new ArrayList<>();
        if(drug.getMedicationPlanDrugs() != null)
        {
            for(MedicationPlanDrug medicationPlanDrug : drug.getMedicationPlanDrugs())
            {
                MedicationPlanDrugDTO medicationPlanDrugDTO = new MedicationPlanDrugDTO(
                    medicationPlanDrug.getId(),
                    medicationPlanDrug.getStartDate(),
                    medicationPlanDrug.getEndDate(),
                    medicationPlanDrug.getIntakeInterval()
                );

                medicationPlanDrugsDTO.add(medicationPlanDrugDTO);
            }
        }

        return new DrugDTO(drug.getId(), drug.getName(), drug.getSideEffects(), drug.getDosage(), medicationPlanDrugsDTO);
    }

    public static Drug toEntity(DrugDTO drugDTO) {
        List<MedicationPlanDrug> medicationPlanDrugs = new ArrayList<>();

        if(Hibernate.isInitialized(drugDTO.getMedicationPlanDrugsDTO()))
        {
            for(MedicationPlanDrugDTO m : drugDTO.getMedicationPlanDrugsDTO())
            {
                medicationPlanDrugs.add(MedicationPlanDrugBuilder.toEntity(m));
            }
        }

        return new Drug(drugDTO.getId(),
                drugDTO.getName(),
                drugDTO.getSideEffects(),
                drugDTO.getDosage(),
                medicationPlanDrugs);
    }
}
