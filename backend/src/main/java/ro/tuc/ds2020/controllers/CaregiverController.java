package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.CaregiverDTO;
import ro.tuc.ds2020.payload.request.CaregiverUpdateRequest;
import ro.tuc.ds2020.payload.request.CaregiverCreateRequest;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.CaregiverService;
import ro.tuc.ds2020.services.UserService;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/caregiver")

public class CaregiverController {

    private final CaregiverService caregiverService;
    private final UserService userService;

    @Autowired
    public CaregiverController(CaregiverService caregiverService, UserService userService) {
        this.caregiverService = caregiverService;
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<CaregiverDTO>> getCaregivers() {
        List<CaregiverDTO> dtos = caregiverService.findCaregivers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody CaregiverCreateRequest caregiverCreateRequest) throws ParseException {
        if(!userService.userExistsById(caregiverCreateRequest.getUserDTOId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));

        UUID caregiverID = caregiverService.insert(caregiverCreateRequest);
        return new ResponseEntity<>(caregiverID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CaregiverDTO> getCaregiver(@PathVariable("id") UUID caregiverID) {
        CaregiverDTO dto = caregiverService.findCaregiverById(caregiverID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updateCaregiver(@Valid @RequestBody CaregiverUpdateRequest caregiverUpdateRequest) throws ParseException {
        if (!caregiverService.caregiverExistsById(caregiverUpdateRequest.getSelectedId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Caregiver with given ID does not exist in the database!"));


        UUID userID = caregiverService.update(caregiverUpdateRequest);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteCaregiver(@Valid @RequestBody GenericDeleteRequest genericDeleteRequest) throws ParseException {
        UUID selectedId = genericDeleteRequest.getSelectedId();
        if (!caregiverService.caregiverExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Caregiver with given ID does not exist in the database!"));


        UUID userID = caregiverService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }
}
