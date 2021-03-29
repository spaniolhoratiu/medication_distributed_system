package ro.tuc.ds2020.controllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth_test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public String patientAccess() {
        return "PATIENT Content.";
    }

    @GetMapping("/caregiver")
    @PreAuthorize("hasRole('ROLE_CAREGIVER')")
    public String caregiverAccess() {
        return "CAREGIVER Board.";
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String doctorAccess() {
        return "DOCTOR Board.";
    }
}