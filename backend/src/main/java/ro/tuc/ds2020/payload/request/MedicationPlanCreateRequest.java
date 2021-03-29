package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class MedicationPlanCreateRequest {
    private String name;
    private UUID selectedId;

    public String getName() {
        return name;
    }

    public UUID getSelectedId() {
        return selectedId;
    }
}
