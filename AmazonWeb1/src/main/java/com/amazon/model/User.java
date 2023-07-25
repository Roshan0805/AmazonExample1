package com.amazon.model;

/**
 * <p> Represents the User </p>
 *
 * @author Roshan
 * @version 1.0
 */
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPhoneNumber(final String phoneNo) {
        this.phoneNumber = phoneNo;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return String.format("user id -%d\nusername - %s\nemail -%s\naddress - %s\nphone no - %s\n", id, name, email, address, phoneNumber);
    }
}