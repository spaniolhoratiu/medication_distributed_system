package ro.tuc.ds2020.payload.request;

public class DrugCreateRequest {
    private String dosage;
    private String name;
    private String sideEffects;

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
