package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Doctor;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    
}
