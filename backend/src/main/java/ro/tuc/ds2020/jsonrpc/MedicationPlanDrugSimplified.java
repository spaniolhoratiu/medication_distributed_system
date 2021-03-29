package ro.tuc.ds2020.jsonrpc;

import java.io.Serializable;

public class MedicationPlanDrugSimplified implements Serializable {
    private String startDate;
    private String endDate;

    private String dosage;
    private String intakeInterval;
    private String drugName;
    private String sideEffects;

    private String patientName;

    public MedicationPlanDrugSimplified(String startDate, String endDate, String dosage, String intakeInterval, String drugName, String sideEffects, String patientName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
        this.intakeInterval = intakeInterval;
        this.drugName = drugName;
        this.sideEffects = sideEffects;
        this.patientName = patientName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
}