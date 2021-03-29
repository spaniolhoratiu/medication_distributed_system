package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.dtos.builders.MedicationPlanBuilder;
import ro.tuc.ds2020.dtos.builders.MedicationPlanDrugBuilder;
import ro.tuc.ds2020.entities.Doctor;
import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.MedicationPlanDrug;
import ro.tuc.ds2020.payload.request.MedicationPlanDrugCreateRequest;
import ro.tuc.ds2020.repositories.DrugRepository;
import ro.tuc.ds2020.repositories.MedicationPlanDrugRepository;
import ro.tuc.ds2020.repositories.MedicationPlanRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicationPlanDrugService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicationPlanDrugService.class);
    private final MedicationPlanDrugRepository medicationPlanDrugRepository;
    private final MedicationPlanRepository medicationPlanRepository;
    private final DrugRepository drugRepository;



    @Autowired
    public MedicationPlanDrugService(MedicationPlanDrugRepository medicationPlanDrugRepository,
                                     MedicationPlanRepository medicationPlanRepository,
                                     DrugRepository drugRepository) {
        this.medicationPlanDrugRepository = medicationPlanDrugRepository;
        this.medicationPlanRepository = medicationPlanRepository;
        this.drugRepository = drugRepository;
    }

    @Transactional
    public List<MedicationPlanDrugDTO> findMedicationPlanDrugs() {
        List<MedicationPlanDrug> medicationPlanDrugList = medicationPlanDrugRepository.findAll();
        return medicationPlanDrugList.stream()
                .map(MedicationPlanDrugBuilder::toMedicationPlanDrugDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicationPlanDrugDTO findMedicationPlanDrugById(UUID id) {
        Optional<MedicationPlanDrug> prosumerOptional = medicationPlanDrugRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("MedicationPlanDrug with id {} was not found in db", id);
            throw new ResourceNotFoundException(MedicationPlanDrug.class.getSimpleName() + " with id: " + id);
        }
        return MedicationPlanDrugBuilder.toMedicationPlanDrugDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(MedicationPlanDrugCreateRequest medicationPlanDrugCreateRequest) throws ParseException {
        MedicationPlan medicationPlan = medicationPlanRepository.findById(medicationPlanDrugCreateRequest.getSelectedMedicationPlanId()).get();
        Drug drug = drugRepository.findById(medicationPlanDrugCreateRequest.getSelectedDrugId()).get();

        Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(medicationPlanDrugCreateRequest.getStartDate());
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(medicationPlanDrugCreateRequest.getEndDate());
        String intakeInterval = medicationPlanDrugCreateRequest.getIntakeInterval();

        MedicationPlanDrug medicationPlanDrug = new MedicationPlanDrug(
                startDate,
                endDate,
                intakeInterval,
                medicationPlan,
                drug
        );

       // MedicationPlanDrug medicationPlanDrug = MedicationPlanDrugBuilder.toEntity(medicationPlanDrugDTO);
        medicationPlanDrug = medicationPlanDrugRepository.save(medicationPlanDrug);
        LOGGER.debug("MedicationPlanDrug with id {} was inserted in db", medicationPlanDrug.getId());
        return medicationPlanDrug.getId();
    }

    @Transactional
    public UUID delete(UUID id)
    {
        MedicationPlanDrug medicationPlanDrug = medicationPlanDrugRepository.findById(id).get();

        medicationPlanDrugRepository.delete(medicationPlanDrug);
        LOGGER.debug("MedicationPlanDrug with id {} was deleted from db", medicationPlanDrug.getId());
        return medicationPlanDrug.getId();
    }

    public boolean medicationPlanDrugExistsById(UUID id)
    {
        return medicationPlanDrugRepository.existsById(id);
    }


}