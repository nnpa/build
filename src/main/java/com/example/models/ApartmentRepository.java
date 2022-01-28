/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ivan
 */
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
    @Query(value = "SELECT * FROM apartment WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    Apartment findByIdAndUserId(Long id, Long user_id);
    

    
    @Query(value = "SELECT * FROM apartment WHERE user_id = ?1", nativeQuery = true)
    List<Apartment> findByUserId(Long user_id);
    
   
}