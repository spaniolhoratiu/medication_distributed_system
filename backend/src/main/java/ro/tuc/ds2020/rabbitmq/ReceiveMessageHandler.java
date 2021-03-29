package ro.tuc.ds2020.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.SocketController;
import ro.tuc.ds2020.dtos.ActivityDTO;
import ro.tuc.ds2020.entities.Activity;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.repositories.PatientRepository;
import ro.tuc.ds2020.services.ActivityService;
import ro.tuc.ds2020.websocket.WebSocketMessage;

import javax.sql.rowset.serial.SerialJavaObject;
import java.io.Serializable;
import java.net.Socket;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@EnableRabbit
public class ReceiveMessageHandler{

    private ActivityService activityService;

    private PatientRepository patientRepository;

    private SocketController socketController;

    @Autowired
    public ReceiveMessageHandler(ActivityService activityService, SocketController socketController, PatientRepository patientRepository){
        this.activityService = activityService;
        this.socketController = socketController;
        this.patientRepository = patientRepository;
    }


    @RabbitListener(queues = { ConfigureRabbitMq.QUEUE_NAME } )
    public void handleMessage(Message message) throws ParseException {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        Object obj = messageConverter.fromMessage(message);
        ActivityDTO activityDTO = (ActivityDTO) obj;

        Optional<Patient> dummyPatient = patientRepository.findById(activityDTO.getPatient_id());
        String patientName;
        if(dummyPatient.isPresent()) {
            Patient patient = dummyPatient.get();
            patientName = patient.getName();
            //TODO: use activity service
            Activity activity = new Activity(activityDTO.getPatient_id(), activityDTO.getActivity(), activityDTO.getStart(), activityDTO.getEnd());
            activityService.insert(activity);
        } else {
            patientName = "Fake Patient Name";
        }

        String suspiciousActivityMotive = getSuspiciousActivityMotive(activityDTO, patientName);
        boolean isActivitySuspicious = !suspiciousActivityMotive.isEmpty();
        if(isActivitySuspicious) {
            log.info(suspiciousActivityMotive);
            WebSocketMessage message1 = new WebSocketMessage(activityDTO.getPatient_id(), patientName, activityDTO.getActivity(), suspiciousActivityMotive);
            //TODO: uncomment for notifications
            socketController.sendToAll(message1);
        }
    }


    private String getSuspiciousActivityMotive(ActivityDTO activityDTO, String patientName)
    {
        String activity = activityDTO.getActivity();
        long startMillis = activityDTO.getStart();
        long endMillis = activityDTO.getEnd();
        UUID patientId = activityDTO.getPatient_id();

        long durationOfActivity = endMillis - startMillis;
        switch (activity) {
            case "Sleeping":
                long sevenHours = 25200000;
                if (durationOfActivity > sevenHours) {
                    return ("Patient " + patientName + " slept for more than 7 hours!");
                }
                break;
            case "Leaving":
                long fiveHours = 18000000;
                if (durationOfActivity > fiveHours) {
                    return ("Patient " + patientName + " left home for more than 5 hours!");
                }
                break;
            case "Toileting":
                long thirtyMinutes = 1800000;
                if (durationOfActivity >= thirtyMinutes) {
                    return ("Patient " + patientName + " has been in the bathroom for more than 30 minutes!");
                }
                break;
            default:
                return "";

        }
        return "";
    }
}
