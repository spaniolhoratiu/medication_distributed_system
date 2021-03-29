package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class DrugUpdateRequest {
    private UUID selectedId;
    private String dosage;
    private String name;
    private String sideEffects;

    public UUID getSelectedId() {
        return selectedId;
    }

    public String getDosage() {
        return dosage;
    }

    public String getName() {
        return name;
    }

    public String getSideEffects() {
        return sideEffects;
    }
}
