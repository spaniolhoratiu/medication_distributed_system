package ro.tuc.ds2020.dtos;



import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.UUID;

public class DrugDTO {
    private UUID id;
    private String name;
    private String sideEffects;
    private String dosage;
    List<MedicationPlanDrugDTO> medicationPlanDrugsDTO;

    public DrugDTO() {
    }

    public DrugDTO(UUID id, String name, String sideEffects, String dosage, List<MedicationPlanDrugDTO> medicationPlanDrugsDTO) {
        this.id = id;
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
        this.medicationPlanDrugsDTO = medicationPlanDrugsDTO;
    }

    public DrugDTO(String name, String sideEffects, String dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
        this.medicationPlanDrugsDTO = new ArrayList<>();
    }

    public DrugDTO(UUID id, String name, String sideEffects, String dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
        this.medicationPlanDrugsDTO = new ArrayList<>();
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

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public List<MedicationPlanDrugDTO> getMedicationPlanDrugsDTO() {
        return medicationPlanDrugsDTO;
    }

    public void setMedicationPlanDrugsDTO(List<MedicationPlanDrugDTO> medicationPlanDrugsDTO) {
        this.medicationPlanDrugsDTO = medicationPlanDrugsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugDTO drugDTO = (DrugDTO) o;
        return id.equals(drugDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
