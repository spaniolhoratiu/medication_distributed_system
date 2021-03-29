package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.MedicationPlanDTO;
import ro.tuc.ds2020.payload.request.MedicationPlanCreateRequest;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.MedicationPlanService;
import ro.tuc.ds2020.services.PatientService;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/medicationPlan")

public class MedicationPlanController {

    private final MedicationPlanService medicationPlanService;
    private final PatientService patientService;


    @Autowired
    public MedicationPlanController(MedicationPlanService medicationPlanService, PatientService patientService) {
        this.medicationPlanService = medicationPlanService;
        this.patientService = patientService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<MedicationPlanDTO>> getMedicationPlans() {
        List<MedicationPlanDTO> dtos = medicationPlanService.findMedicationPlans();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody MedicationPlanCreateRequest medicationPlanCreateRequest) {
        if(!patientService.patientExistsById(medicationPlanCreateRequest.getSelectedId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: MedicationPlan with given ID does not exist in the database!"));
        
        UUID medicationPlanID = medicationPlanService.insert(medicationPlanCreateRequest);
        return new ResponseEntity<>(medicationPlanID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicationPlanDTO> getMedicationPlan(@PathVariable("id") UUID medicationPlanID) {
        MedicationPlanDTO dto = medicationPlanService.findMedicationPlanById(medicationPlanID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


//    @PutMapping()
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
//    public ResponseEntity<?> updateMedicationPlan(@Valid @RequestBody MedicationPlanUpdateRequest medicationPlanUpdateRequest) throws ParseException {
//        if (!medicationPlanService.medicationPlanExistsById(medicationPlanUpdateRequest.getSelectedId()))
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: MedicationPlan with given ID does not exist in the database!"));
//
//
//        UUID userID = medicationPlanService.update(medicationPlanUpdateRequest);
//        return new ResponseEntity<>(userID, HttpStatus.OK);
//    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteMedicationPlan(@Valid @RequestBody GenericDeleteRequest genericDeleteRequest) throws ParseException {
        UUID selectedId = genericDeleteRequest.getSelectedId();
        if (!medicationPlanService.medicationPlanExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: MedicationPlan with given ID does not exist in the database!"));


        UUID userID = medicationPlanService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }
}
