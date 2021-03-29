package ro.tuc.ds2020.payload.request;

import java.util.UUID;

public class GenericDeleteRequest {
    private UUID selectedId;

    public UUID getSelectedId() {
        return selectedId;
    }
}
