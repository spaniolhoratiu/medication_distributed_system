package ro.tuc.ds2020.dtos.builders;


import ro.tuc.ds2020.dtos.DrugDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDTO;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.MedicationPlanDrug;

public class MedicationPlanDrugBuilder {

    private MedicationPlanDrugBuilder() {
    }

    public static MedicationPlanDrugDTO toMedicationPlanDrugDTO(MedicationPlanDrug medicationPlanDrug) {
        MedicationPlanDTO medicationPlanDTO = MedicationPlanBuilder.toMedicationPlanDTO(medicationPlanDrug.getMedicationPlan());
        DrugDTO drugDTO = DrugBuilder.toDrugDTO(medicationPlanDrug.getDrug());

        return new MedicationPlanDrugDTO(medicationPlanDrug.getId(), medicationPlanDrug.getStartDate(), medicationPlanDrug.getEndDate(), medicationPlanDrug.getIntakeInterval(), medicationPlanDTO, drugDTO);
    }

    public static MedicationPlanDrug toEntity(MedicationPlanDrugDTO medicationPlanDrugDTO) {
        MedicationPlan medicationPlanEntity = MedicationPlanBuilder.toEntity(medicationPlanDrugDTO.getMedicationPlanDTO());
        Drug drugEntity = DrugBuilder.toEntity(medicationPlanDrugDTO.getDrugDTO());

        return new MedicationPlanDrug(medicationPlanDrugDTO.getId(),
                medicationPlanDrugDTO.getStartDate(),
                medicationPlanDrugDTO.getEndDate(),
                medicationPlanDrugDTO.getIntakeInterval(),
                medicationPlanEntity,
                drugEntity
        );
    }
}
