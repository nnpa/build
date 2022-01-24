/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ivan
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    User findByEmailAddress(String emailAddress);
    
    @Query(value = "SELECT * FROM user WHERE activation_string = ?1", nativeQuery = true)
    User findByActivationString(String activationString);
    
    @Query(value = "SELECT * FROM user WHERE reset_password_string = ?1", nativeQuery = true)
    User findByResetPasswordString(String resetPasswordString);
    
    
}
