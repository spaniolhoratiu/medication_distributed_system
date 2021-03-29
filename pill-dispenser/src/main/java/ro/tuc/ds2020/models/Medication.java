package ro.tuc.ds2020.models;

import java.util.Date;

public class Medication {
    private Date startDate;
    private Date endDate;

    private String dosage;
    private String intakeInterval;
    private String drugName;
    private String sideEffects;

    private String patientName;

    public Medication(Date startDate, Date endDate, String dosage, String intakeInterval, String drugName, String sideEffects, String patientName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
        this.intakeInterval = intakeInterval;
        this.drugName = drugName;
        this.sideEffects = sideEffects;
        this.patientName = patientName;
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getIntakeInterval() {
        return intakeInterval;
    }

    public void setIntakeInterval(String intakeInterval) {
        this.intakeInterval = intakeInterval;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return drugName;
    }
}
