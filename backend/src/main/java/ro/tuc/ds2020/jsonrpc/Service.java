package ro.tuc.ds2020.jsonrpc;

import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.entities.MedicationPlanDrug;

import java.util.List;
import java.util.UUID;

public interface Service {

    public List<MedicationPlanDrugSimplified> getMedicationPlanDrugsOfTodayOfPatient(String stringPatientId, String stringCurrentDate);

    public void logMedicationTaken(String patientName, String medicationName, String dateTimeWhenMedicationShouldHaveBeenTaken, String dateTimeWhenMedicationWasTaken);

    public void logMedicationNotTaken(String patientName, String medicationName, String dateTimeWhenMedicationShouldHaveBeenTaken, String currentDateTime);
}