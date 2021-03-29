package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DoctorDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DoctorBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Doctor;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.payload.request.DoctorCreateRequest;
import ro.tuc.ds2020.payload.request.DoctorUpdateRequest;
import ro.tuc.ds2020.repositories.DoctorRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;


    @Autowired
    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<DoctorDTO> findDoctors() {
        List<Doctor> doctorList = doctorRepository.findAll();
        return doctorList.stream()
                .map(DoctorBuilder::toDoctorDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorDTO findDoctorById(UUID id) {
        Optional<Doctor> prosumerOptional = doctorRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db", id);
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id: " + id);
        }
        return DoctorBuilder.toDoctorDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(DoctorCreateRequest doctorCreateRequest) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(doctorCreateRequest.getBirthDate());
        UserDTO userDTO = UserBuilder.toUserDTO(userRepository.findById(doctorCreateRequest.getUserDTOId()).get());
        DoctorDTO doctorDTO = new DoctorDTO(
                doctorCreateRequest.getName(),
                date,
                doctorCreateRequest.getGender(),
                doctorCreateRequest.getAddress(),
                userDTO
                );

        Doctor doctor = DoctorBuilder.toEntity(doctorDTO);
        doctor.setUser(userRepository.findById(doctorCreateRequest.getUserDTOId()).get());
        doctor = doctorRepository.save(doctor);
        LOGGER.debug("Doctor with id {} was inserted in db", doctor.getId());
        return doctor.getId();
    }

    @Transactional
    public UUID update(DoctorUpdateRequest doctorUpdateRequest) throws ParseException {
        Doctor doctor = doctorRepository.findById(doctorUpdateRequest.getSelectedId()).get();

        String requestAddress = doctorUpdateRequest.getAddress();
        String requestBirthDate = doctorUpdateRequest.getBirthDate();
        String requestGender = doctorUpdateRequest.getGender();
        String requestName = doctorUpdateRequest.getName();

        if(!requestAddress.isEmpty())
            doctor.setAddress(requestAddress);
        if(!requestBirthDate.isEmpty())
            doctor.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(requestBirthDate));
        if(!requestGender.isEmpty())
            doctor.setGender(requestGender);
        if(!requestName.isEmpty())
            doctor.setName(requestName);

        doctor = doctorRepository.save(doctor);
        LOGGER.debug("Doctor with id {} was updated in db", doctor.getId());
        return doctor.getId();
    }

    @Transactional
    public UUID delete(UUID id)
    {
        Doctor doctor = doctorRepository.findById(id).get();

        doctorRepository.delete(doctor);
        LOGGER.debug("Doctor with id {} was deleted from db", doctor.getId());
        return doctor.getId();
    }


    public boolean doctorExistsById(UUID id)
    {
        return doctorRepository.existsById(id);
    }


}