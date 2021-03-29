package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class MedicationPlanDrugCreateRequest {
    private UUID selectedMedicationPlanId;
    private UUID selectedDrugId;
    private String startDate;
    private String endDate;
    private String intakeInterval;

    public UUID getSelectedMedicationPlanId() {
        return selectedMedicationPlanId;
    }

    public UUID getSelectedDrugId() {
        return selectedDrugId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getIntakeInterval() {
        return intakeInterval;
    }
}
