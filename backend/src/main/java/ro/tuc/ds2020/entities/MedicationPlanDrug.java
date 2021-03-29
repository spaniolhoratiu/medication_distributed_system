package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class MedicationPlanDrug {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "intake_interval", nullable = false)
    private String intakeInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="medication_plan_id", nullable = false)
    private MedicationPlan medicationPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="drug_id", nullable = false)
    private Drug drug;

    public MedicationPlanDrug() {
    }

    public MedicationPlanDrug(UUID id, Date startDate, Date endDate, String intakeInterval, MedicationPlan medicationPlan, Drug drug) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intakeInterval = intakeInterval;
        this.medicationPlan = medicationPlan;
        this.drug = drug;
    }

    public MedicationPlanDrug(Date startDate, Date endDate, String intakeInterval, MedicationPlan medicationPlan, Drug drug) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.intakeInterval = intakeInterval;
        this.medicationPlan = medicationPlan;
        this.drug = drug;
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

    public MedicationPlan getMedicationPlan() {
        return medicationPlan;
    }

    public void setMedicationPlan(MedicationPlan medicationPlan) {
        this.medicationPlan = medicationPlan;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
