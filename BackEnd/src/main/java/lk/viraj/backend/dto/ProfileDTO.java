package lk.viraj.backend.dto;

import java.util.UUID;

public class ProfileDTO {
    private UUID uid;
    private String email;
    private String name;
    private String contact;
    private String address;

    public ProfileDTO() {
    }

    public ProfileDTO(UUID uid, String email, String name, String contact, String address) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
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
}
