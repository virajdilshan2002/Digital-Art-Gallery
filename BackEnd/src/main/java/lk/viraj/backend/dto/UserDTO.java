package lk.viraj.backend.dto;

import java.sql.Date;

public class  UserDTO {
    private String email;
    private String password;
    private String name;
    private String contact;
    private String address;
    private String nic;
    private Date dob;
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
        this.dob = dob;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
                ", dob=" + dob +
                ", role='" + role + '\'' +
                '}';
    }
}
