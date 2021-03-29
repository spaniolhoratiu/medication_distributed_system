package ro.tuc.ds2020.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class RegisterRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}