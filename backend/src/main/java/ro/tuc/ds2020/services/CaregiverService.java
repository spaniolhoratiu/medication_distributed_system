package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.CaregiverDTO;
import ro.tuc.ds2020.dtos.CaregiverDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.CaregiverBuilder;
import ro.tuc.ds2020.dtos.builders.CaregiverBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.payload.request.CaregiverCreateRequest;
import ro.tuc.ds2020.payload.request.CaregiverUpdateRequest;
import ro.tuc.ds2020.payload.request.PatientCaregiverRequest;
import ro.tuc.ds2020.repositories.CaregiverRepository;
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
public class CaregiverService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverService.class);
    private final CaregiverRepository caregiverRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public CaregiverService(CaregiverRepository caregiverRepository, UserRepository userRepository, PatientRepository patientRepository) {
        this.caregiverRepository = caregiverRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public List<CaregiverDTO> findCaregivers() {
        List<Caregiver> caregiverList = caregiverRepository.findAll();
        return caregiverList.stream()
                .map(CaregiverBuilder::toCaregiverDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CaregiverDTO findCaregiverById(UUID id) {
        Optional<Caregiver> prosumerOptional = caregiverRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db", id);
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id: " + id);
        }
        return CaregiverBuilder.toCaregiverDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(CaregiverCreateRequest caregiverCreateRequest) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(caregiverCreateRequest.getBirthDate());
        UserDTO userDTO = UserBuilder.toUserDTO(userRepository.findById(caregiverCreateRequest.getUserDTOId()).get());
        CaregiverDTO caregiverDTO = new CaregiverDTO(
                caregiverCreateRequest.getName(),
                date,
                caregiverCreateRequest.getGender(),
                caregiverCreateRequest.getAddress(),
                userDTO
        );

        Caregiver caregiver = CaregiverBuilder.toEntity(caregiverDTO);
        caregiver.setUser(userRepository.findById(caregiverCreateRequest.getUserDTOId()).get());
        caregiver = caregiverRepository.save(caregiver);
        LOGGER.debug("Caregiver with id {} was inserted in db", caregiver.getId());
        return caregiver.getId();
    }

    @Transactional
    public UUID update(CaregiverUpdateRequest caregiverUpdateRequest) throws ParseException {
        Caregiver caregiver = caregiverRepository.findById(caregiverUpdateRequest.getSelectedId()).get();

        String requestAddress = caregiverUpdateRequest.getAddress();
        String requestBirthDate = caregiverUpdateRequest.getBirthDate();
        String requestGender = caregiverUpdateRequest.getGender();
        String requestName = caregiverUpdateRequest.getName();

        if(!requestAddress.isEmpty())
            caregiver.setAddress(requestAddress);
        if(!requestBirthDate.isEmpty())
            caregiver.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(requestBirthDate));
        if(!requestGender.isEmpty())
            caregiver.setGender(requestGender);
        if(!requestName.isEmpty())
            caregiver.setName(requestName);

        caregiver = caregiverRepository.save(caregiver);
        LOGGER.debug("Caregiver with id {} was updated in db", caregiver.getId());
        return caregiver.getId();
    }

    @Transactional
    public UUID delete(UUID id)
    {
        Caregiver caregiver = caregiverRepository.findById(id).get();

        caregiverRepository.delete(caregiver);
        LOGGER.debug("Caregiver with id {} was deleted from db", caregiver.getId());
        return caregiver.getId();
    }


    @Transactional
    public UUID addPatient(PatientCaregiverRequest patientCaregiverRequest)
    {
        Patient patient = patientRepository.findById(patientCaregiverRequest.getPatientId()).get();
        Caregiver caregiver = caregiverRepository.findById(patientCaregiverRequest.getCaregiverId()).get();

        caregiver.getPatients().add(patient);
        caregiver = caregiverRepository.save(caregiver);

        LOGGER.debug("Caregiver with id {} was associated with Patient with id {} from db", patient.getId(), caregiver.getId());
        return caregiver.getId();
    }


    public boolean caregiverExistsById(UUID id)
    {
        return caregiverRepository.existsById(id);
    }

}