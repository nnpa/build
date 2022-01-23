/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import java.util.Set;
import com.example.models.Role;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
/**
 *
 * @author ivan
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private boolean active;
    private boolean avtivated;
    private String activation_string;

    public boolean isAvtivated() {
        return avtivated;
    }

    public void setAvtivated(boolean avtivated) {
        this.avtivated = avtivated;
    }

    public String getActivation_string() {
        return activation_string;
    }

    public void setActivation_string(String activation_string) {
        this.activation_string = activation_string;
    }
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRolse() {
        return role;
    }

    public void setRolse(Set<Role> role) {
        this.role = role;
    }
    
}
