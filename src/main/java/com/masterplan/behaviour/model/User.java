package com.masterplan.behaviour.model;

import java.time.LocalDate; 
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Table("users")
public class User {

    @Id
    @Column("id")
    @JsonProperty("id")
    private Integer id;

    @Column("username")
    @NotEmpty(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @JsonProperty("username")
    private String username;

    @Column("password")
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    //@JsonIgnore
    @JsonProperty("password")
    private String password;

    @Column("role")
    @JsonProperty("role")
    private String role;

    @Column("last_login")
    @JsonProperty("last_login")
    private LocalDateTime lastLogin; 

    @Column("photo")
    @JsonProperty("photo")
    private String photo;

    @Column("born")
    @JsonProperty("born")
    private LocalDate born; 

    @Column("first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column("middle_name")
    @JsonProperty("middle_name")
    private String middleName;

    @Column("last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column("gender")
    @JsonProperty("gender")
    private String gender;

    @Column("email")
    @JsonProperty("email")
    private String email;

    @Column("nida")
    @JsonProperty("nida")
    private String nida;

    @Column("phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;



    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(Integer id, String username, String password,String role, LocalDateTime lastLogin, String photo, LocalDate born, String firstName, String middleName, String lastName,String gender,String email, String nida, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLogin = lastLogin;
        this.photo = photo;
        this.born = born;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.nida = nida;
        this.phoneNumber = phoneNumber;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setRole(String role){
        this.role=role;
    }


    // setters
    public void setFirstname(String fname){
        this.firstName=fname;
    }
    public void setMiddlename(String mname){
        this.middleName=mname;
    }
    public void setLastname(String lname){
        this.lastName=lname;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setBorn(LocalDate born){
        this.born=born;
    }
    public void setPhone(String phone){
        this.phoneNumber=phone;
    }

    // Getters
    @JsonProperty("firstname")
    public String getFirstname() {
        return this.firstName;
    }

    @JsonProperty("middlename")
    public String getMiddlename() {
        return this.middleName;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return this.lastName;    
    }

    @JsonProperty("gender")
    public String getGender() {
        return this.gender;
    }

    @JsonProperty("born")
    public LocalDate getBorn() {
        return this.born;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return this.phoneNumber;
    }

    @JsonProperty("photo")
    public String getPhoto() {
        return this.photo;
    }

    @JsonProperty("role")
    public String getUserRole() {
        return role;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("password")
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\''+
                ", lastLogin=" + lastLogin +
                ", photo='" + photo + '\'' +
                ", born=" + born +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\''+
                ", email='" + email + '\''+
                ", nida='" + nida + '\''+
                ", phone='" + phoneNumber + '\''+
                '}';
    }


}
