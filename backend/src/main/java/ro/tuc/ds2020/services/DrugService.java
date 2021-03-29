package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DrugDTO;
import ro.tuc.ds2020.dtos.builders.DrugBuilder;
import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.entities.Drug;
import ro.tuc.ds2020.payload.request.DrugCreateRequest;
import ro.tuc.ds2020.payload.request.DrugUpdateRequest;
import ro.tuc.ds2020.repositories.DrugRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DrugService.class);
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Transactional
    public List<DrugDTO> findDrugs() {
        List<Drug> drugList = drugRepository.findAll();
        return drugList.stream()
                .map(DrugBuilder::toDrugDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DrugDTO findDrugById(UUID id) {
        Optional<Drug> prosumerOptional = drugRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Drug with id {} was not found in db", id);
            throw new ResourceNotFoundException(Drug.class.getSimpleName() + " with id: " + id);
        }
        return DrugBuilder.toDrugDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(DrugCreateRequest drugCreateRequest) {
        DrugDTO drugDTO = new DrugDTO(drugCreateRequest.getName(), drugCreateRequest.getSideEffects(), drugCreateRequest.getDosage());
        Drug drug = DrugBuilder.toEntity(drugDTO);
        
        drug = drugRepository.save(drug);
        LOGGER.debug("Drug with id {} was inserted in db", drug.getId());
        return drug.getId();
    }

    @Transactional
    public UUID update(DrugUpdateRequest drugUpdateRequest) {
        Drug drug = drugRepository.findById(drugUpdateRequest.getSelectedId()).get();
        
        String requestName = drugUpdateRequest.getName();
        String requestDosage = drugUpdateRequest.getDosage();
        String requestSideEffects = drugUpdateRequest.getSideEffects();
        
        if(!requestDosage.isEmpty())
            drug.setDosage(requestDosage);
        if(!requestName.isEmpty())
            drug.setName(requestName);
        if(!requestSideEffects.isEmpty())
            drug.setSideEffects(requestSideEffects);


        drug = drugRepository.save(drug);
        LOGGER.debug("Drug with id {} was inserted in db", drug.getId());
        return drug.getId();
    }

    @Transactional
    public UUID delete(UUID id)
    {
        Drug drug = drugRepository.findById(id).get();

        drugRepository.delete(drug);
        LOGGER.debug("Drug with id {} was deleted from db", drug.getId());
        return drug.getId();
    }

    public boolean drugExistsById(UUID id)
    {
        return drugRepository.existsById(id);
    }

}