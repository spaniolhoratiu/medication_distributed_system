package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.CaregiverDTO;
import ro.tuc.ds2020.dtos.PatientCaregiverDTO;
import ro.tuc.ds2020.dtos.PatientDTO;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.request.PatientCaregiverRequest;
import ro.tuc.ds2020.payload.request.PatientUpdateRequest;
import ro.tuc.ds2020.payload.request.PatientCreateRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.CaregiverService;
import ro.tuc.ds2020.services.PatientService;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/patient")

public class PatientController {

    private final PatientService patientService;
    private final UserService userService;
    private final CaregiverService caregiverService;

    @Autowired
    public PatientController(PatientService patientService, UserService userService, CaregiverService caregiverService) {
        this.patientService = patientService;
        this.userService = userService;
        this.caregiverService = caregiverService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getPatients() {
        List<PatientDTO> dtos = patientService.findPatients();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/caregiver")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_CAREGIVER')")
    public ResponseEntity<List<PatientCaregiverDTO>> getPatientCaregivers() {
        List<PatientDTO> patientDTOS = patientService.findPatients();
        List<PatientCaregiverDTO> dtosToBeSent = new ArrayList<>();
        for(PatientDTO patientDTO : patientDTOS)
        {
            List<CaregiverDTO> caregiverDTOS = patientDTO.getCaregiversDTO();
            if(caregiverDTOS != null && caregiverDTOS.size() > 0)
            {
                for(CaregiverDTO caregiverDTO : caregiverDTOS)
                {
                    PatientCaregiverDTO patientCaregiverDTO = new PatientCaregiverDTO(patientDTO, caregiverDTO);
                    dtosToBeSent.add(patientCaregiverDTO);
                }
            }
        }

        return new ResponseEntity<>(dtosToBeSent, HttpStatus.OK);
    }

    @GetMapping(value = "/with_user_id/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getPatient(@PathVariable("id") UUID userID) {
        if(!userService.userExistsById(userID))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));

        PatientDTO dto = patientService.findPatientByUserId(userID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody PatientCreateRequest patientCreateRequest) throws ParseException {
        if(!userService.userExistsById(patientCreateRequest.getUserDTOId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));

        UUID patientID = patientService.insert(patientCreateRequest);
        return new ResponseEntity<>(patientID, HttpStatus.CREATED);
    }


    @PostMapping(value = "/caregiver")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody PatientCaregiverRequest patientCaregiverRequest) throws ParseException {
        if(!patientService.patientExistsById(patientCaregiverRequest.getPatientId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Patient with given ID does not exist in the database!"));
        if(!caregiverService.caregiverExistsById(patientCaregiverRequest.getCaregiverId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Caregiver with given ID does not exist in the database!"));

        UUID patientID = patientService.addCaregiver(patientCaregiverRequest);
        return new ResponseEntity<>(patientID, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/caregiver")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deletePatientCaregiver(@Valid @RequestBody PatientCaregiverRequest patientCaregiverRequest) throws ParseException {
        if(!patientService.patientExistsById(patientCaregiverRequest.getPatientId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Patient with given ID does not exist in the database!"));
        if(!caregiverService.caregiverExistsById(patientCaregiverRequest.getCaregiverId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Caregiver with given ID does not exist in the database!"));

        UUID patientID = patientService.deleteCaregiver(patientCaregiverRequest);
        return new ResponseEntity<>(patientID, HttpStatus.CREATED);
    }


    @PutMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updatePatient(@Valid @RequestBody PatientUpdateRequest patientUpdateRequest) throws ParseException {
        if (!patientService.patientExistsById(patientUpdateRequest.getSelectedId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Patient with given ID does not exist in the database!"));


        UUID userID = patientService.update(patientUpdateRequest);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deletePatient(@Valid @RequestBody GenericDeleteRequest patientDeleteRequest) throws ParseException {
        UUID selectedId = patientDeleteRequest.getSelectedId();
        if (!patientService.patientExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Patient with given ID does not exist in the database!"));


        UUID userID = patientService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }


}
