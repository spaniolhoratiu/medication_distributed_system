package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DrugDTO;
import ro.tuc.ds2020.payload.request.DrugUpdateRequest;
import ro.tuc.ds2020.payload.request.DrugCreateRequest;
import ro.tuc.ds2020.payload.request.DrugUpdateRequest;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.DrugService;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/drug")

public class DrugController {

    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<DrugDTO>> getDrugs() {
        List<DrugDTO> dtos = drugService.findDrugs();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody DrugCreateRequest drugCreateRequest) {
        UUID drugID = drugService.insert(drugCreateRequest);
        return new ResponseEntity<>(drugID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<DrugDTO> getDrug(@PathVariable("id") UUID drugID) {
        DrugDTO dto = drugService.findDrugById(drugID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PutMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updateDrug(@Valid @RequestBody DrugUpdateRequest drugUpdateRequest) throws ParseException {
        if (!drugService.drugExistsById(drugUpdateRequest.getSelectedId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Drug with given ID does not exist in the database!"));


        UUID userID = drugService.update(drugUpdateRequest);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteDrug(@Valid @RequestBody GenericDeleteRequest genericDeleteRequest) throws ParseException {
        UUID selectedId = genericDeleteRequest.getSelectedId();
        if (!drugService.drugExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Drug with given ID does not exist in the database!"));


        UUID userID = drugService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }
}
