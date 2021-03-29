package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.entities.MedicationPlan;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MedicationPlanDrugDTO {
    private UUID id;
    private Date startDate;
    private Date endDate;
    private String intakeInterval;
    private MedicationPlanDTO medicationPlanDTO;
    private DrugDTO drugDTO;

    public MedicationPlanDrugDTO() {
    }

    public MedicationPlanDrugDTO(UUID id, Date startDate, Date endDate, String intakeInterval, MedicationPlanDTO medicationPlanDTO, DrugDTO drugDTO) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intakeInterval = intakeInterval;
        this.medicationPlanDTO = medicationPlanDTO;
        this.drugDTO = drugDTO;
    }

    public MedicationPlanDrugDTO(UUID id, Date startDate, Date endDate, String intakeInterval, DrugDTO drugDTO) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intakeInterval = intakeInterval;
        this.drugDTO = drugDTO;
    }

    public MedicationPlanDrugDTO(UUID id, Date startDate, Date endDate, String intakeInterval) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intakeInterval = intakeInterval;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIntakeInterval() {
        return intakeInterval;
    }

    public void setIntakeInterval(String intakeInterval) {
        this.intakeInterval = intakeInterval;
    }

    public MedicationPlanDTO getMedicationPlanDTO() {
        return medicationPlanDTO;
    }

    public void setMedicationPlanDTO(MedicationPlanDTO medicationPlanDTO) {
        this.medicationPlanDTO = medicationPlanDTO;
    }

    public DrugDTO getDrugDTO() {
        return drugDTO;
    }

    public void setDrug(DrugDTO drugDTO) {
        this.drugDTO = drugDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationPlanDrugDTO that = (MedicationPlanDrugDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
