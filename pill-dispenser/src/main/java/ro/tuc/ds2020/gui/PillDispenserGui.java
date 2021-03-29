package ro.tuc.ds2020.gui;

import com.rabbitmq.tools.jsonrpc.JsonRpcClient;
import lombok.SneakyThrows;
import org.springframework.cglib.core.Local;
import ro.tuc.ds2020.SpaniolHoratiuAssignment3PillDispenserApplication;
import ro.tuc.ds2020.models.Medication;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class PillDispenserGui {

    public String loggedUserId;
    public String loggedPatientEmail;
    public JPanel mainPanel;

    private JLabel patientNameLabel;
    private JLabel datetimeLabel;
    private JLabel updatingDateAndTimeLabel;


    private JList medsList;
    private JPanel medsPanel;
    private JPanel dateTimePanel;
    private JButton takeButton;
    private JLabel nextTakeLabel;
    private JButton dummyGetDrugsButton;
    private JLabel nextTakeHolderLabel;
    private JLabel myMedicationsLabel;
    private DefaultListModel<Medication> medsListModel;

    private LocalDateTime currentDateTime;
    private Map<Medication, List<LocalDateTime>> medicationToTimeMap;
    private JsonRpcClient client;

    public Timer timer;

    private final static int MINUTES_AFTER_DEADLINE_ALLOWED_TO_TAKE_MEDICATION = 60;
    private final static int HOUR_TO_DOWNLOAD_MEDICATIONS = 16;
    private final static int MINUTE_TO_DOWNLOAD_MEDICATIONS = 17;
    private final static int SECOND_TO_DOWNLOAD_MEDICATIONS = 30;

    public PillDispenserGui(String loggedUserId, String loggedPatientEmail) {
        setUpJsonRpcClient();
        setUpMiscellaneous(loggedUserId, loggedPatientEmail);
        setUpClock();
        setUpMedicationList();
        setUpTakeButton();
        setUpDummyGetDrugsButton();
    }

    @SneakyThrows
    private void setUpJsonRpcClient()
    {
        client = new JsonRpcClient(SpaniolHoratiuAssignment3PillDispenserApplication.channel, "", SpaniolHoratiuAssignment3PillDispenserApplication.QUEUE);
    }

    private void setUpDummyGetDrugsButton()
    {
        dummyGetDrugsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getDrugsOfLoggedPatient();
            }
        });

    }

    private void setUpTakeButton()
    {
        takeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Medication medication = (Medication) medsList.getSelectedValue();
                if(medication == null)
                {
                    JOptionPane.showMessageDialog(null, "You need to select a medicine to take!");
                }else
                {
                    alertRPCMedicationTaken(
                            medication.getPatientName(),
                            medication.getDrugName(),
                            localDateTimeToString(medicationToTimeMap.get(medication).get(0)),
                            localDateTimeToString(currentDateTime)
                            );

                    List<LocalDateTime> timesToTakeMedicine = medicationToTimeMap.get(medication);
                    timesToTakeMedicine.remove(timesToTakeMedicine.get(0));
                    medicationToTimeMap.put(medication, timesToTakeMedicine);
                    removeFromJListIfPatientDoesntHaveToTakeMedicationToday(medication);
                }

                nextTakeLabel.setText("");
            }
        });
    }


    private void setUpMiscellaneous(String loggedUserId, String loggedPatientEmail)
    {
        mainPanel.setPreferredSize(new Dimension(400, 600));
        patientNameLabel.setText(loggedPatientEmail);
        this.loggedPatientEmail = loggedPatientEmail;
        this.loggedUserId = loggedUserId;
    }


    private void setUpMedicationList()
    {
        medsListModel = new DefaultListModel<Medication>();
        medsList.setModel(medsListModel);


        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Medication selectedMed = (Medication) medsList.getSelectedValue();
                    if(selectedMed != null)
                    {
                        LocalDateTime nextTakeDateTime = medicationToTimeMap.get(selectedMed).get(0);
                        String nextTakeString = localDateTimeToString(nextTakeDateTime);
                        nextTakeLabel.setText(nextTakeString);
                    }
                    else
                    {
                        nextTakeLabel.setText("");
                    }
                }
            }
        };
        medsList.addMouseListener(mouseListener);

        medsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medsList.setVisible(true);
    }


    private void setUpClock()
    {
        ActionListener timerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentDateTime = LocalDateTime.now();
                String currentDateTimeString = localDateTimeToString(currentDateTime);
                updatingDateAndTimeLabel.setText(currentDateTimeString);

                checkTimeAndFetchDrugsForToday();

                removeAndAlertNotTakenMedicine();
            }
        };

        timer = new Timer(1000, timerActionListener);
        timer.setInitialDelay(0);
        timer.start();
    }

    private void checkTimeAndFetchDrugsForToday()
    {
        if(currentDateTime.getHour() == HOUR_TO_DOWNLOAD_MEDICATIONS &&
                currentDateTime.getMinute() == MINUTE_TO_DOWNLOAD_MEDICATIONS &&
                currentDateTime.getSecond() == SECOND_TO_DOWNLOAD_MEDICATIONS)
        {
            getDrugsOfLoggedPatient();
        }
    }

    private void removeAndAlertNotTakenMedicine()
    {
        for(int i = 0; i < medsListModel.size(); i++)
        {
            Medication currentMedication = medsListModel.get(i);
            List<LocalDateTime> timesOfCurrentMedication = medicationToTimeMap.get(currentMedication);

            ListIterator<LocalDateTime> iter = timesOfCurrentMedication.listIterator();
            while(iter.hasNext()){
                LocalDateTime currentLocalDateTimeWhenMedicationShouldHaveBeenTaken = iter.next();
                if(currentDateTime.isAfter(currentLocalDateTimeWhenMedicationShouldHaveBeenTaken.plusMinutes(MINUTES_AFTER_DEADLINE_ALLOWED_TO_TAKE_MEDICATION)))
                {
                    alertRPCMedicationNotTaken(
                            currentMedication.getPatientName(),
                            currentMedication.getDrugName(),
                            localDateTimeToString(currentLocalDateTimeWhenMedicationShouldHaveBeenTaken),
                            localDateTimeToString(currentDateTime));
                    iter.remove();
                }


            }

            medicationToTimeMap.put(currentMedication, timesOfCurrentMedication);
            removeFromJListIfPatientDoesntHaveToTakeMedicationToday(currentMedication);
        }
    }

    private void removeFromJListIfPatientDoesntHaveToTakeMedicationToday(Medication medication)
    {
        if(medicationToTimeMap.get(medication).isEmpty())
        {
            medsListModel.removeElement(medication);
        }
    }

    @SneakyThrows
    private void alertRPCMedicationNotTaken(String patientName, String drugName, String dateTimeWhenMedicationShouldHaveBeenTaken, String currentDateTime)
    {
        String method = "logMedicationNotTaken";
        String[] arguments = { patientName, drugName, dateTimeWhenMedicationShouldHaveBeenTaken, currentDateTime };
        client.call(method, arguments);
    }


    @SneakyThrows
    private void alertRPCMedicationTaken(String patientName, String drugName, String dateTimeWhenMedicationShouldHaveBeenTaken, String dateTimeWhenMedicationWasTaken)
    {
        String method = "logMedicationTaken";
        String[] arguments = { patientName, drugName, dateTimeWhenMedicationShouldHaveBeenTaken, dateTimeWhenMedicationWasTaken };
        client.call(method, arguments);
    }



    private String localDateTimeToString(LocalDateTime localTimeDate)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localTimeDate.format(dateTimeFormatter);
    }


    @SneakyThrows
    private void getDrugsOfLoggedPatient()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDateTimeString = currentDateTime.format(dateTimeFormatter);

        String method = "getMedicationPlanDrugsOfTodayOfPatient";
            String[] arguments = { loggedUserId, currentDateTimeString };
        ArrayList<HashMap> result = (ArrayList<HashMap>) client.call(method, arguments);

        System.out.println("Getting result: " + result);



        medsListModel.removeAllElements();
        for(HashMap hashmap: result)
        {
            String startDateTime = hashmap.get("startDate").toString().split(" ")[0];
            String endDateTime =  hashmap.get("endDate").toString().split(" ")[0];

            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateTime);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateTime);

            Medication medication = new Medication(
                    startDate,
                    endDate,
                hashmap.get("dosage").toString(),
                hashmap.get("intakeInterval").toString(),
                hashmap.get("drugName").toString(),
                hashmap.get("sideEffects").toString(),
                hashmap.get("patientName").toString()
                );


            medsListModel.addElement(medication);
        }

        computeTimesForToday();

        // Note:
        // Call client.getServiceDescription() to get a ServiceDescription that
        // describes all the remote methods that the JSON-RPC server provides.

//        client.close();
//        SpaniolHoratiuAssignment3PillDispenserApplication.channel.close();
//        SpaniolHoratiuAssignment3PillDispenserApplication.connection.close();
    }

    private void computeTimesForToday()
    {
        medicationToTimeMap = new HashMap<>();
        for(int i = 0; i < medsListModel.getSize(); i++)
        {

            Medication currentMedication = medsListModel.get(i);
            medicationToTimeMap.put(currentMedication, new ArrayList<>());
            long intakeIntervalLong = Long.parseLong(currentMedication.getIntakeInterval().split(" ")[0]);

            LocalDateTime startDate = LocalDateTime.ofInstant(currentMedication.getStartDate().toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(currentMedication.getEndDate().toInstant(),
                    ZoneId.systemDefault());

            LocalDateTime takeAt = startDate.plusHours(intakeIntervalLong);
            while(takeAt.isBefore(endDate))
            {
                if(takeAt.getDayOfMonth() == currentDateTime.getDayOfMonth())
                {
                    List<LocalDateTime> timesToTake = medicationToTimeMap.get(currentMedication);
                    timesToTake.add(takeAt);
                    medicationToTimeMap.put(currentMedication, timesToTake);
                }

                takeAt = takeAt.plusHours(intakeIntervalLong);
            }
        }
    }

}
