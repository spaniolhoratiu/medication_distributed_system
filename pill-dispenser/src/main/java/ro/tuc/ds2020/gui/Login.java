package ro.tuc.ds2020.gui;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Login{
    private JTextField emailField;
    private JLabel emailLabel;
    private JPanel emailPanel;
    private JPanel passwordPanel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    public JPanel mainPanel;

    public String loggedPatientId;
    public String loggedPatientEmail;

    public static final String BACKEND_LOGIN_URI = "https://ds2020-horatiuspaniol-1.herokuapp.com/auth/login";


    public Login()
    {
        loggedPatientId = "";
        loggedPatientEmail = "";

        mainPanel.setPreferredSize(new Dimension(400,600));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();

                boolean isLoginSuccesful = sendPostLoginRequest(BACKEND_LOGIN_URI, email, password);
                if(isLoginSuccesful)
                {
                    closeLoginWindow();
                    openPillDispenserGui(getLoggedPatientId(), getLoggedPatientEmail());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Login failed. You did not enter a patient account.");
                }
            }
        });
    }

    private void closeLoginWindow()
    {
        mainPanel.setVisible(false);
    }

    private void openPillDispenserGui(String patientId, String loggedPatientEmail)
    {
        PillDispenserGui pillDispenserGui = new PillDispenserGui(patientId, loggedPatientEmail);
        JFrame mainFrame = new JFrame("Pill Dispenser");
        mainFrame.setContentPane(pillDispenserGui.mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    @SneakyThrows
    private boolean sendPostLoginRequest(String uri, String email, String password) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(uri);

        StringEntity params = new StringEntity("{\"email\":\"" + email +  "\",\"password\":\"" + password +"\"} ");
        httppost.setEntity(params);
        httppost.addHeader("content-type", "application/json");

        //Execute and get the response.
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Login failed. Connection to backend failed.");
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();


        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                // do something useful
                String result = convertStreamToString(instream);
                JSONObject myObject = new JSONObject(result);
                String receivedPatientId = myObject.getString("id");
                String receivedEmail = myObject.getString("email");
                String receivedRole = myObject.getString("role");

                if(!receivedRole.equals("[ROLE_PATIENT]")) {
                    this.setLoggedPatientId("");
                    this.setLoggedPatientEmail("");
                    return false;
                }
                else {
                    this.setLoggedPatientId(receivedPatientId);
                    this.setLoggedPatientEmail(receivedEmail);
                }
            }
            return true;
        }

        return false;
    }

    @SneakyThrows
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public String getLoggedPatientId() {
        return loggedPatientId;
    }

    public void setLoggedPatientId(String loggedPatientId) {
        this.loggedPatientId = loggedPatientId;
    }

    public String getLoggedPatientEmail() {
        return loggedPatientEmail;
    }

    public void setLoggedPatientEmail(String loggedPatientEmail) {
        this.loggedPatientEmail = loggedPatientEmail;
    }


}
