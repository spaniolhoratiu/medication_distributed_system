package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.request.MedicationPlanDrugCreateRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.DrugService;
import ro.tuc.ds2020.services.MedicationPlanDrugService;
import ro.tuc.ds2020.services.MedicationPlanService;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/medicationPlanDrug")

public class MedicationPlanDrugController {

    private final MedicationPlanDrugService medicationPlanDrugService;
    private final DrugService drugService;
    private final MedicationPlanService medicationPlanService;

    @Autowired
    public MedicationPlanDrugController(MedicationPlanDrugService medicationPlanDrugService, DrugService drugService, MedicationPlanService medicationPlanService) {
        this.medicationPlanDrugService = medicationPlanDrugService;
        this.drugService = drugService;
        this.medicationPlanService = medicationPlanService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<MedicationPlanDrugDTO>> getMedicationPlanDrugs() {
        List<MedicationPlanDrugDTO> dtos = medicationPlanDrugService.findMedicationPlanDrugs();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody MedicationPlanDrugCreateRequest medicationPlanDrugCreateRequest) throws ParseException {
        if(!drugService.drugExistsById(medicationPlanDrugCreateRequest.getSelectedDrugId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Drug with given ID does not exist in the database!"));
        if(!medicationPlanService.medicationPlanExistsById(medicationPlanDrugCreateRequest.getSelectedMedicationPlanId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Medication Plan with given ID does not exist in the database!"));

        UUID medicationPlanDrugID = medicationPlanDrugService.insert(medicationPlanDrugCreateRequest);
        return new ResponseEntity<>(medicationPlanDrugID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicationPlanDrugDTO> getMedicationPlanDrug(@PathVariable("id") UUID medicationPlanDrugID) {
        MedicationPlanDrugDTO dto = medicationPlanDrugService.findMedicationPlanDrugById(medicationPlanDrugID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteMedicationPlanDrug(@Valid @RequestBody GenericDeleteRequest genericDeleteRequest) throws ParseException {
        UUID selectedId = genericDeleteRequest.getSelectedId();
        if (!medicationPlanDrugService.medicationPlanDrugExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: MedicationPlanDrug with given ID does not exist in the database!"));


        UUID userID = medicationPlanDrugService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }
    
}
