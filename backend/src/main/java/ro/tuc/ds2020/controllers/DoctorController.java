package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DoctorDTO;
import ro.tuc.ds2020.payload.request.DoctorCreateRequest;
import ro.tuc.ds2020.payload.request.GenericDeleteRequest;
import ro.tuc.ds2020.payload.request.DoctorUpdateRequest;
import ro.tuc.ds2020.payload.response.MessageResponse;
import ro.tuc.ds2020.services.DoctorService;
import ro.tuc.ds2020.services.UserService;


import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/doctor")

public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;

    @Autowired
    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<DoctorDTO> dtos = doctorService.findDoctors();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> insertProsumer(@Valid @RequestBody DoctorCreateRequest doctorCreateRequest) throws ParseException {
        if(!userService.userExistsById(doctorCreateRequest.getUserDTOId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with given ID does not exist in the database!"));

        UUID doctorID = doctorService.insert(doctorCreateRequest);
        return new ResponseEntity<>(doctorID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("id") UUID doctorID) {
        DoctorDTO dto = doctorService.findDoctorById(doctorID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updateDoctor(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) throws ParseException {
        if (!doctorService.doctorExistsById(doctorUpdateRequest.getSelectedId()))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Doctor with given ID does not exist in the database!"));


        UUID userID = doctorService.update(doctorUpdateRequest);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> deleteDoctor(@Valid @RequestBody GenericDeleteRequest genericDeleteRequest) throws ParseException {
        UUID selectedId = genericDeleteRequest.getSelectedId();
        if (!doctorService.doctorExistsById(selectedId))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Doctor with given ID does not exist in the database!"));


        UUID userID = doctorService.delete(selectedId);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

}
