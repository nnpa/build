/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import com.example.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.models.UserRepository;
import com.example.models.User;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
        @Autowired
        private UserRepository userRepository;
        
        @Autowired
        private Environment env;
        
	@GetMapping("/registration")
	public String registration() {
		return "user/registration";
	}
        
        @GetMapping("/settings")
	public String settings(Model model,Principal principal) {
            User user = userRepository.findByEmailAddress(principal.getName());
            String uuid = UUID.randomUUID().toString();
            user.setReset_password_string(uuid);
            userRepository.save(user);
            model.addAttribute("uid", uuid);
            
            return "user/resetpassword_form_confirm";
	}
        
        @RequestMapping(value = "/postsettings", method = RequestMethod.POST)
	public String postsettings(@RequestParam String uid,@RequestParam String password,@RequestParam String password2,Model model,Principal principal) {
            
            User user = userRepository.findByEmailAddress(principal.getName());
            List<String> errors = new ArrayList<String>();
            
            if(!password.equals(password2)){
                errors.add("Пароли не совпадают");
            }
            if(password.length() < 3){
                errors.add("Пароль меньше 3 символов");
            }

            
            model.addAttribute("errors", errors);
            model.addAttribute("uid", uid);

            if(errors.size() == 0){
                user.setPassword(password);
                userRepository.save(user);
                return "user/password_reseted";
            }
            
            
            return "user/resetpassword_form_confirm";
	}
        
        @GetMapping("/resetpassword")
	public String resetpassword(Model model) {
             List<String> errors = new ArrayList<String>();
             model.addAttribute("errors", errors);
             return "user/resetpassword_form";
        }
        
        @GetMapping("/resetpasswordform")
	public String resetpasswordform(@RequestParam String uid,Model model) {
             List<String> errors = new ArrayList<String>();
             User user = userRepository.findByResetPasswordString(uid);
            
             if(user == null){
                errors.add("Пользователь не найден в базе");
             }
                
             model.addAttribute("errors", errors);
             model.addAttribute("uid", uid);

             return "user/resetpassword_form_confirm";
        }
        
        @RequestMapping(value = "/postresetpasswordform", method = RequestMethod.POST)
	public String postresetpasswordform(@RequestParam String password,@RequestParam String password2,@RequestParam String uid,Model model) throws MessagingException {
            List<String> errors = new ArrayList<String>();
            User user = userRepository.findByResetPasswordString(uid);
            if(user == null){
                errors.add("Пользователь не найден в базе");
            }
            if(!password.equals(password2)){
                errors.add("Пароли не совпадают");
            }
            if(password.length() < 3){
                errors.add("Пароль меньше 3 символов");
            }
            
            model.addAttribute("uid", uid);
            model.addAttribute("errors", errors);
            
            if(errors.size() == 0){
                user.setPassword(password);
                userRepository.save(user);
                return "user/password_reseted";
            }
            
            
            return "user/resetpassword_form_confirm";
        }
        
        @RequestMapping(value = "/postresetpassword", method = RequestMethod.POST)
	public String postregistration(@RequestParam String email,Model model) throws MessagingException {
            List<String> errors = new ArrayList<String>();
            User user = userRepository.findByEmailAddress(email);
            
            if(user == null){
                errors.add("email не найден в базе");
            }
            if(errors.size() == 0){
                String uuid = UUID.randomUUID().toString();
                user.setReset_password_string(uuid);
                userRepository.save(user);
                
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                mailSender.setHost("smtp.gmail.com");
                mailSender.setPort(587);
                mailSender.setUsername("springbootemailsender777@gmail.com");
                mailSender.setPassword("g02091988");

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", "true");
                properties.setProperty("mail.smtp.starttls.enable", "true");

                mailSender.setJavaMailProperties(properties);

                String from = "no-reply@builder";

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

                helper.setSubject("Восстановление пароля");
                helper.setFrom(from);
                helper.setTo(email);

                String url = env.getProperty("url");

                boolean html = true;
                helper.setText("<a href='" + url + "/resetpasswordform?uid=" + uuid + "'>Сбросить пароль</a>", html);
                
                mailSender.send(message);
            }
            model.addAttribute("errors", errors);
            return "user/resetpassword_form";
        }
        
        @GetMapping("/confirm")
	public String confirm(@RequestParam String uid) {
            if(uid == ""){
                return "redirect:/";
            }
            
            User user = userRepository.findByActivationString(uid);
            
            if(user == null){
                return "redirect:/";
            }
            
            if(user.isActivated()){
                return "redirect:/";
            }
            
            user.setActivated(true);
            userRepository.save(user);
            
            return "user/confirm";
	}
        
        @RequestMapping(value = "/postregistration", method = RequestMethod.POST)
	public String postregistration(@RequestParam String email,@RequestParam String password,@RequestParam String password2,Model model) throws MessagingException {
                List<String> errors = new ArrayList<String>();
                
                User user = userRepository.findByEmailAddress(email);
                if(user != null){
                    errors.add("email уже зарегистрирован");
                }
                
                if(!password.equals(password2)){
                    errors.add("Пароли не совпадают");
                }
                
                if(password.length() < 3){
                    errors.add("Пароль меньше 3 символов");
                }
                
                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher mat = pattern.matcher(email);
                if(!mat.matches()){
                    errors.add("Не валидный email");
                }
                
                if(errors.size() == 0){
                    Set<Role> roles = new HashSet<>();
                    roles.add(Role.USER);
                    String uuid = UUID.randomUUID().toString();

                    user = new User();
                    user.setRole(roles);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setActive(true);
                    user.setActivated(false);
                    user.setActivation_string(uuid);
                    user.setReset_password_string("");
                    userRepository.save(user);
                    
                    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                    mailSender.setHost("smtp.gmail.com");
                    mailSender.setPort(587);
                    mailSender.setUsername("springbootemailsender777@gmail.com");
                    mailSender.setPassword("g02091988");

                    Properties properties = new Properties();
                    properties.setProperty("mail.smtp.auth", "true");
                    properties.setProperty("mail.smtp.starttls.enable", "true");

                    mailSender.setJavaMailProperties(properties);
                    
                    String from = "no-reply@builder";

                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

                    helper.setSubject("Подтверждение регистрации на сайте");
                    helper.setFrom(from);
                    helper.setTo(email);
                    
                    String url = env.getProperty("url");

                    boolean html = true;
                    helper.setText("<a href='" + url + "/confirm?uid=" + uuid + "'>Подтвердить регистрацию</a>", html);

                    mailSender.send(message);
                    return "user/send_confirm";

                }
                
                
                model.addAttribute("email",email);

                model.addAttribute("errors",errors);
		return "user/registration";
	}

}