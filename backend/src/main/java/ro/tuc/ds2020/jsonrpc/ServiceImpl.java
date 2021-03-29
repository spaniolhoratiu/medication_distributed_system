package ro.tuc.ds2020.jsonrpc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import ro.tuc.ds2020.controllers.MedicationPlanController;
import ro.tuc.ds2020.dtos.MedicationPlanDrugDTO;
import ro.tuc.ds2020.entities.MedicationPlan;
import ro.tuc.ds2020.entities.MedicationPlanDrug;
import ro.tuc.ds2020.repositories.MedicationPlanDrugRepository;
import ro.tuc.ds2020.services.MedicationPlanDrugService;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class ServiceImpl implements Service {

    public ServiceImpl() {
    }

    @Override
    public void logMedicationTaken(String patientName,
                                   String medicationName,
                                   String dateTimeWhenMedicationShouldHaveBeenTaken,
                                   String dateTimeWhenMedicationWasTaken) {
        String medicationTakenInfo = "\n Patient " + patientName
                    + " has taken " + medicationName
                    + " according to its plan."
                    + "\n Programmed at: " + dateTimeWhenMedicationShouldHaveBeenTaken
                    + "\n Took at: " + dateTimeWhenMedicationWasTaken;
        log.info(medicationTakenInfo);
    }

    @Override
    public void logMedicationNotTaken(String patientName,
                                      String medicationName,
                                      String dateTimeWhenMedicationShouldHaveBeenTaken,
                                      String currentDateTime) {
        String medicationNotTakenInfo = "\n Patient " + patientName
                + " has NOT taken " + medicationName
                + " according to its plan."
                + "\n Programmed at: " + dateTimeWhenMedicationShouldHaveBeenTaken
                + "\n Current Time and Date: " + currentDateTime;
        log.info(medicationNotTakenInfo);
    }

    @SneakyThrows
    @Override
    public List<MedicationPlanDrugSimplified> getMedicationPlanDrugsOfTodayOfPatient(String stringUserId, String stringCurrentDate) {
        
        MedicationPlanDrugService service = SpringContext.getBean(MedicationPlanDrugService.class);

        UUID userId = UUID.fromString(stringUserId);

        List<MedicationPlanDrugDTO> medicationPlanDrugsDTOS = service.findMedicationPlanDrugs();
        List<MedicationPlanDrugDTO> medicationPlanDrugsDTOSOfPatient = medicationPlanDrugsDTOS.stream().filter(med -> med.getMedicationPlanDTO().getPatientDTO().getUserDTO().getId().equals(userId)).collect(Collectors.toList());

        Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringCurrentDate);
        List<MedicationPlanDrugDTO> medicationPlanDrugsDTOSOfPatientFilteredByTime = medicationPlanDrugsDTOSOfPatient.stream().filter(med -> med.getEndDate().after(currentDate)).collect(Collectors.toList());

        return simplify(medicationPlanDrugsDTOSOfPatientFilteredByTime);
    }


    private List<MedicationPlanDrugSimplified> simplify(List<MedicationPlanDrugDTO> medicationPlanDrugDTOS)
    {
        List<MedicationPlanDrugSimplified> simpleMeds = new ArrayList<>();
        for(MedicationPlanDrugDTO dto : medicationPlanDrugDTOS)
        {
            MedicationPlanDrugSimplified simpleMed = new MedicationPlanDrugSimplified(
                    dto.getStartDate().toString(),
                    dto.getEndDate().toString(),
                    dto.getDrugDTO().getDosage(),
                    dto.getIntakeInterval(),
                    dto.getDrugDTO().getName(),
                    dto.getDrugDTO().getSideEffects(),
                    dto.getMedicationPlanDTO().getPatientDTO().getName()
            );
            
            simpleMeds.add(simpleMed);
        }


        return simpleMeds;
    }

}
