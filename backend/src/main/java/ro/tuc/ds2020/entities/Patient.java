package ro.tuc.ds2020.entities;

import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.*;

@Entity
public class Patient  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "medical_record", nullable = false)
    private String medicalRecord;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name="patient_caregiver",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "caregiver_id")
    )
    private List<Caregiver> caregivers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<MedicationPlan> medicationPlans;

    public Patient() {
    }

    public Patient(String name, Date birthDate, String gender, String address, String medicalRecord, User user, List<Caregiver> caregivers, List<MedicationPlan> medicationPlans) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalRecord = medicalRecord;
        this.user = user;
        this.caregivers = caregivers;
        this.medicationPlans = medicationPlans;
    }


    public Patient(String name, Date birthDate, String gender, String address, String medicalRecord, User user) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.medicalRecord = medicalRecord;
        this.user = user;
        this.caregivers = new ArrayList<>();
        this.medicationPlans = new ArrayList<>();
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Caregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(List<Caregiver> caregivers) {
        this.caregivers = caregivers;
    }

    public List<MedicationPlan> getMedicationPlans() {
        return medicationPlans;
    }

    public void setMedicationPlans(List<MedicationPlan> medicationPlans) {
        this.medicationPlans = medicationPlans;
    }
}
