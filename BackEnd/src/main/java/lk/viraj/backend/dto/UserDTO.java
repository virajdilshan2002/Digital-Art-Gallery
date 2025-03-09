package lk.viraj.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class  UserDTO {
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should contain at least one digit, one lowercase letter, one uppercase letter, one special character and should be 8 characters long")
    private String password;
    @Pattern(regexp = "^[A-za-z ]+$", message = "Name should only contain letters and spaces")
    @Size(min = 4, max = 30, message = "Name should contain 4 to 30 characters")
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should contain 10 digits")
    private String contact;
    private String address;
    private String nic;
    private String role;

    public UserDTO() {
    }

    public UserDTO(String email, String password, String name, String contact, String address, String nic, Date dob, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.nic = nic;
        this.role = role;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
