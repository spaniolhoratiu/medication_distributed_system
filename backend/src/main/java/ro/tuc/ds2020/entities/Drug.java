package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Drug {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "side_effects")
    private String sideEffects;

    @Column(name = "dosage", nullable = false)
    private String dosage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drug", cascade = CascadeType.ALL)
    private List<MedicationPlanDrug> medicationPlanDrugs;

    public Drug() {
    }

    public Drug(UUID id, String name, String sideEffects, String dosage, List<MedicationPlanDrug> medicationPlanDrugs)
    {
        this.id = id;
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
        this.medicationPlanDrugs = medicationPlanDrugs;
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

    public List<MedicationPlanDrug> getMedicationPlanDrugs() {
        return medicationPlanDrugs;
    }

    public void setMedicationPlanDrugs(List<MedicationPlanDrug> medicationPlanDrugs) {
        this.medicationPlanDrugs = medicationPlanDrugs;
    }
}
