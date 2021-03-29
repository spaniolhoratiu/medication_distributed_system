package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.MedicationPlanDTO;
import ro.tuc.ds2020.dtos.PatientDTO;
import ro.tuc.ds2020.dtos.builders.MedicationPlanBuilder;
import ro.tuc.ds2020.dtos.builders.MedicationPlanDrugBuilder;
import ro.tuc.ds2020.dtos.builders.PatientBuilder;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.payload.request.MedicationPlanCreateRequest;
import ro.tuc.ds2020.repositories.MedicationPlanRepository;
import ro.tuc.ds2020.repositories.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicationPlanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicationPlanService.class);
    private final MedicationPlanRepository medicationPlanRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public MedicationPlanService(MedicationPlanRepository medicationPlanRepository, PatientRepository patientRepository) {
        this.medicationPlanRepository = medicationPlanRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public List<MedicationPlanDTO> findMedicationPlans() {
        List<MedicationPlan> medicationPlanList = medicationPlanRepository.findAll();
        return medicationPlanList.stream()
                .map(MedicationPlanBuilder::toMedicationPlanDTO)
                .collect(Collectors.toList());
    }

    public MedicationPlanDTO findMedicationPlanById(UUID id) {
        Optional<MedicationPlan> prosumerOptional = medicationPlanRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("MedicationPlan with id {} was not found in db", id);
            throw new ResourceNotFoundException(MedicationPlan.class.getSimpleName() + " with id: " + id);
        }
        return MedicationPlanBuilder.toMedicationPlanDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(MedicationPlanCreateRequest medicationPlanCreateRequest) {
//        Patient patient = patientRepository.findById(medicationPlanCreateRequest.getSelectedId()).get();
//        PatientDTO patientDTO = PatientBuilder.toPatientDTO(patient);
//
//        MedicationPlanDTO medicationPlanDTO = new MedicationPlanDTO(medicationPlanCreateRequest.getName(), patientDTO);
//        MedicationPlan medicationPlan = MedicationPlanBuilder.toEntity(medicationPlanDTO);
//
//        medicationPlan.setPatient(patient);
//
//        medicationPlan = medicationPlanRepository.save(medicationPlan);
//        LOGGER.debug("MedicationPlan with id {} was inserted in db", medicationPlan.getId());
//        return medicationPlan.getId();
//

        Patient patient = patientRepository.findById(medicationPlanCreateRequest.getSelectedId()).get();

        MedicationPlan medicationPlan = new MedicationPlan(medicationPlanCreateRequest.getName(), patient);

        medicationPlan = medicationPlanRepository.save(medicationPlan);
        LOGGER.debug("MedicationPlan with id {} was inserted in db", medicationPlan.getId());
        return medicationPlan.getId();
    }

//    public UUID update(MedicationPlanUpdateRequest medicationPlanUpdateRequest) {
//        MedicationPlan medicationPlan = medicationPlanRepository.findById(medicationPlanUpdateRequest.getSelectedId()).get();
//
//        String requestName = medicationPlanUpdateRequest.getName();
//        String requestDosage = medicationPlanUpdateRequest.getDosage();
//        String requestSideEffects = medicationPlanUpdateRequest.getSideEffects();
//
//        if(!requestDosage.isEmpty())
//            medicationPlan.setDosage(requestDosage);
//        if(!requestName.isEmpty())
//            medicationPlan.setName(requestName);
//        if(!requestSideEffects.isEmpty())
//            medicationPlan.setSideEffects(requestSideEffects);
//
//
//        medicationPlan = medicationPlanRepository.save(medicationPlan);
//        LOGGER.debug("MedicationPlan with id {} was inserted in db", medicationPlan.getId());
//        return medicationPlan.getId();
//    }

    @Transactional
    public UUID delete(UUID id)
    {
        MedicationPlan medicationPlan = medicationPlanRepository.findById(id).get();

        medicationPlanRepository.delete(medicationPlan);
        LOGGER.debug("MedicationPlan with id {} was deleted from db", medicationPlan.getId());
        return medicationPlan.getId();
    }

    public boolean medicationPlanExistsById(UUID id)
    {
        return medicationPlanRepository.existsById(id);
    }

}