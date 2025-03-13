package lk.viraj.backend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String contact;
    private String address;
    private String nic;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Item> items;

    public User() {
    }

    public User(UUID uid, String email, String password, String name, String contact, String address, String nic, Date dob, String role) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.nic = nic;
        this.role = role;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}