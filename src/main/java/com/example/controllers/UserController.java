/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.models.UserRepository;
import com.example.models.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
        @Autowired
        private UserRepository userRepository;
        
	@GetMapping("/registration")
	public String registration() {
		return "user/registration";
	}
        
        @RequestMapping(value = "/postregistration", method = RequestMethod.POST)
	public String postregistration(@RequestParam String email) {
                //User user = userRepository.findByEmailAddress("");
                System.out.println(email);
		return "user/registration";
	}

}