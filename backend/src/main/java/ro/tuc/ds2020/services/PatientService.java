package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.*;
import ro.tuc.ds2020.dtos.builders.PatientBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.payload.request.PatientCaregiverRequest;
import ro.tuc.ds2020.payload.request.PatientUpdateRequest;
import ro.tuc.ds2020.payload.request.PatientCreateRequest;
import ro.tuc.ds2020.repositories.CaregiverRepository;
//import ro.tuc.ds2020.repositories.PatientCaregiverRepository;
import ro.tuc.ds2020.repositories.PatientRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final CaregiverRepository caregiverRepository;
  //  private final PatientCaregiverRepository patientCaregiverRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository, UserRepository userRepository, CaregiverRepository caregiverRepository/*, PatientCaregiverRepository patientCaregiverRepository*/) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.caregiverRepository = caregiverRepository;
    //    this.patientCaregiverRepository = patientCaregiverRepository;
    }

    @Transactional
    public List<PatientDTO> findPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream()
                .map(PatientBuilder::toPatientDTO)
                .collect(Collectors.toList());
    }

        //    @Transactional
        //    public List<PatientCaregiverDTO> findPatientCaregivers() {
        //        List<PatientCaregiverDTO> patientCaregivers = patientCaregiverRepository.findAllPatientCaregivers();
        //        return patientCaregivers;
        //    }

    @Transactional
    public PatientDTO findPatientById(UUID id) {
        Optional<Patient> prosumerOptional = patientRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db", id);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id: " + id);
        }
        return PatientBuilder.toPatientDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(PatientCreateRequest patientCreateRequest) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(patientCreateRequest.getBirthDate());
        UserDTO userDTO = UserBuilder.toUserDTO(userRepository.findById(patientCreateRequest.getUserDTOId()).get());
        
        PatientDTO patientDTO = new PatientDTO(
                patientCreateRequest.getName(),
                date,
                patientCreateRequest.getGender(),
                patientCreateRequest.getAddress(),
                patientCreateRequest.getMedicalRecord(),
                userDTO
        );

        Patient patient = PatientBuilder.toEntity(patientDTO);
        patient.setUser(userRepository.findById(patientCreateRequest.getUserDTOId()).get());
        patient = patientRepository.save(patient);
        LOGGER.debug("Patient with id {} was inserted in db", patient.getId());
        return patient.getId();
    }

    @Transactional
    public UUID update(PatientUpdateRequest patientUpdateRequest) throws ParseException {
        Patient patient = patientRepository.findById(patientUpdateRequest.getSelectedId()).get();

        String requestAddress = patientUpdateRequest.getAddress();
        String requestBirthDate = patientUpdateRequest.getBirthDate();
        String requestGender = patientUpdateRequest.getGender();
        String requestName = patientUpdateRequest.getName();
        String requestMedicalRecord = patientUpdateRequest.getMedicalRecord();

        if(!requestAddress.isEmpty())
            patient.setAddress(requestAddress);
        if(!requestBirthDate.isEmpty())
            patient.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(requestBirthDate));
        if(!requestGender.isEmpty())
            patient.setGender(requestGender);
        if(!requestName.isEmpty())
            patient.setName(requestName);
        if(!requestMedicalRecord.isEmpty())
            patient.setMedicalRecord(requestMedicalRecord);

        patient = patientRepository.save(patient);
        LOGGER.debug("Patient with id {} was updated in db", patient.getId());
        return patient.getId();
    }

    @Transactional
    public UUID delete(UUID id)
    {
        Patient patient = patientRepository.findById(id).get();

        patientRepository.delete(patient);
        LOGGER.debug("Patient with id {} was deleted from db", patient.getId());
        return patient.getId();
    }

    @Transactional
    public UUID addCaregiver(PatientCaregiverRequest patientCaregiverRequest)
    {
        Patient patient = patientRepository.findById(patientCaregiverRequest.getPatientId()).get();
        Caregiver caregiver = caregiverRepository.findById(patientCaregiverRequest.getCaregiverId()).get();

        patient.getCaregivers().add(caregiver);
        patient = patientRepository.save(patient);

        LOGGER.debug("Patient with id {} was associated with Caregiver with id {} from db", patient.getId(), caregiver.getId());
        return patient.getId();
    }

    @Transactional
    public UUID deleteCaregiver(PatientCaregiverRequest patientCaregiverRequest)
    {
        Patient patient = patientRepository.findById(patientCaregiverRequest.getPatientId()).get();
        Caregiver caregiver = caregiverRepository.findById(patientCaregiverRequest.getCaregiverId()).get();

        patient.getCaregivers().remove(caregiver);
        caregiver.getPatients().remove(patient);

        patient = patientRepository.save(patient);
        patientRepository.flush();
        caregiver = caregiverRepository.save(caregiver);

        LOGGER.debug("Patient with id {} was associated with Caregiver with id {} from db", patient.getId(), caregiver.getId());
        return patient.getId();
    }

    @Transactional
    public PatientDTO findPatientByUserId(UUID userId)
    {
        List<Patient> allPatients = patientRepository.findAll();
        Patient patient = allPatients.stream().filter(currPatient -> currPatient.getUser().getId().equals(userId)).findFirst().get();
        return PatientBuilder.toPatientDTO(patient);
    }


    public boolean patientExistsById(UUID id)
    {
        return patientRepository.existsById(id);
    }



}
