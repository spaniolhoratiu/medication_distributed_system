package ro.tuc.ds2020.dtos;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.UUID;

public class MedicationPlanDTO {
    private UUID id;
    private String name;
    private PatientDTO patientDTO;
    List<MedicationPlanDrugDTO> medicationPlanDrugsDTO;
    
    public MedicationPlanDTO() {
    }

    public MedicationPlanDTO(UUID id, String name, PatientDTO patientDTO, List<MedicationPlanDrugDTO> medicationPlanDrugsDTO) {
        this.id = id;
        this.name = name;
        this.patientDTO = patientDTO;
        this.medicationPlanDrugsDTO = medicationPlanDrugsDTO;
    }

    public MedicationPlanDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MedicationPlanDTO(String name, PatientDTO patientDTO) {
        this.name = name;
        this.patientDTO = patientDTO;
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

    public PatientDTO getPatientDTO() {
        return patientDTO;
    }

    public void setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
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
        MedicationPlanDTO that = (MedicationPlanDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
