package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class MedicationPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicationPlan", cascade = CascadeType.ALL)
    private List<MedicationPlanDrug> medicationPlanDrugs;

    public MedicationPlan() {
    }

    public MedicationPlan(UUID id, String name, Patient patient, List<MedicationPlanDrug> medicationPlanDrugs) {
        this.id = id;
        this.name = name;
        this.patient = patient;
        this.medicationPlanDrugs = medicationPlanDrugs;
    }

    public MedicationPlan(String name, Patient patient) {
        this.name = name;
        this.patient = patient;
        this.medicationPlanDrugs = new ArrayList<>();
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<MedicationPlanDrug> getDrugs() {
        return medicationPlanDrugs;
    }

    public void setDrugs(List<MedicationPlanDrug> medicationPlanDrugs) {
        this.medicationPlanDrugs = medicationPlanDrugs;
    }
}
