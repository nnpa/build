/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import java.util.Set;
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
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rooms;
    private int cost;
    private String description;
    private int square;
    private int floor;
    private int city;
    private int address;
    private String phone;
    private Long user_id;

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }
    
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    private boolean vip;
    private int create_time;

    @ElementCollection(targetClass = Img.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Img> images;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public Set<Img> getImages() {
        return images;
    }

    public void setImages(Set<Img> images) {
        this.images = images;
    }
    
    
}
