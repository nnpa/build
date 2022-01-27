/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import com.example.models.Apartment;
import com.example.models.ApartmentRepository;
import com.example.models.Img;
import com.example.models.ImgRepository;
import com.example.models.User;
import com.example.models.UserRepository;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author ivan
 */
@Controller
public class ApartmentController {
    HashMap<Integer, String> cities = new HashMap<>();

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApartmentRepository apartmentRepository; 
    
    @Autowired
    private ImgRepository imgRepository;
        
    @Autowired
    private Environment env;
    
    private void loadCities(){
        cities.put(1, "Москва");
        cities.put(2, "Санкт-Петербург");
        cities.put(3, "Новосибирск");
        cities.put(4, "Екатеринбург");
        cities.put(5, "Казань");
        cities.put(6, "Нижний Новгород");
        cities.put(7, "Челябинск");
        cities.put(8, "Самара");
        cities.put(9, "Омск");
        cities.put(10, "Ростов-на-Дону");
        cities.put(11, "Уфа");
        cities.put(12, "Красноярск");
        cities.put(13, "Воронеж");
        cities.put(14, "Пермь");
        cities.put(15, "Волгоград");
        cities.put(16, "Краснодар");
        cities.put(17, "Саратов");
        cities.put(18, "Тюмень");
        cities.put(19, "Тольятти");
        cities.put(20, "Ижевск");
        cities.put(21, "Барнаул");
        cities.put(22, "Ульяновск");
        cities.put(23, "Иркутск");
        cities.put(24, "Хабаровск");
        cities.put(25, "Махачкала");
        cities.put(26, "Ярославль");
        cities.put(27, "Владивосток");
        cities.put(28, "Оренбург");
        cities.put(29, "Томск");
        cities.put(30, "Кемерово");
        cities.put(31, "Новокузнецк");
        cities.put(32, "Рязань");
        cities.put(33, "Астрахань");
        cities.put(34, "Киров");
        cities.put(35, "Пенза");
        cities.put(36, "Севастополь");
        cities.put(37, "Балашиха");
        cities.put(38, "Липецк");
        cities.put(39, "Чебоксары");
        cities.put(40, "Калининград");
        cities.put(41, "Тула");
        cities.put(42, "Ставрополь");
        cities.put(43, "Курск");
        cities.put(44, "Улан-Удэ");
        cities.put(45, "Сочи");
    }
    
    @GetMapping("/deleteimage/{id}/")
    public String deleteimage(@PathVariable Long id,Principal principal,Model model,HttpServletRequest request) {
        User user = userRepository.findByEmailAddress(principal.getName());
        Optional<Img> optionalImg = imgRepository.findById(id);
        Img img = optionalImg.get();
        Apartment apartment = img.getApartment();
        
        if(apartment.getUser_id() == user.getId()){
            String uploadPath = env.getProperty("uploa.path");

            File imgFile = new File(uploadPath + "/" + img.getPath());
            imgFile.delete();
            imgRepository.delete(img);
            
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id,Principal principal,Model model,HttpServletRequest request) {
        User user = userRepository.findByEmailAddress(principal.getName());
        Apartment apartment = apartmentRepository.findByIdAndUserId(id,user.getId());
        String uploadPath = env.getProperty("uploa.path");

        for(Img img:apartment.getImages()){
            File imgFile = new File(uploadPath + "/" + img.getPath());
            imgFile.delete();
            imgRepository.delete(img);
        }
        apartmentRepository.delete(apartment);
        
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
    
    @GetMapping("/add")
    public String add(Model model) {
        loadCities();
        
        model.addAttribute("city", 1);
        
        model.addAttribute("cities", cities);
        model.addAttribute("isNew", true);

        return "apartment/add";
    }
    
    @PostMapping("/postedit")
    public String postadd(
                @RequestParam("files") MultipartFile[] files,
                @RequestParam int rooms,
                @RequestParam int square,
                @RequestParam int floor,
                @RequestParam int city,
                @RequestParam String address,
                @RequestParam int cost,
                @RequestParam String phone,
                @RequestParam String description,
                @RequestParam Long id,
                Principal principal,
                Model model) throws IOException {
    
            loadCities();
            
            List<String> errors = new ArrayList<String>();
            if(rooms < 1){
                errors.add("Комнат не заполенено");
}
            if(square < 1){
                errors.add("Площадь не заполенено");
            }
            if(floor < 1){
                errors.add("Этаж не заполенено");
            }
            if(address.length() < 1){
                errors.add("Адрес не заполенено");
            }
            if(cost < 1){
                errors.add("Цена не заполенено");
            }
            if(phone.length() < 1){
                errors.add("Телефон не заполенено");
            }
            
            if(errors.size() == 0){
                Optional<Apartment> apartmentOptional = apartmentRepository.findById(id);
                Apartment apartment = apartmentOptional.get();
                
                apartment.setRooms(rooms);
                apartment.setSquare(square);
                apartment.setFloor(floor);
                apartment.setAddress(address);
                apartment.setCost(cost);
                apartment.setCity(city);
                apartment.setPhone(phone);
                apartment.setVip(false);
                apartment.setDescription(description);

                long unixTime = System.currentTimeMillis() / 1000L;
                apartment.setCreate_time(unixTime);
                apartmentRepository.save(apartment);

                for(MultipartFile file:files){
                    String uploadPath = env.getProperty("uploa.path");

                    File uploadDir = new File(uploadPath);

                    if(!uploadDir.exists()){
                        uploadDir.mkdir();
                    }
                    String uuidFile = UUID.randomUUID().toString();
                    String resultFilename = uuidFile + "." + file.getOriginalFilename();
                    String smallResultFilename = "small_" + uuidFile + "." + file.getOriginalFilename();

                    File uploadedFile = new File(uploadPath + "/" + resultFilename);
                    File smallUploadedFile = new File(uploadPath + "/" + smallResultFilename);

                    file.transferTo(uploadedFile);

                    Thumbnails.of(uploadedFile)
                            .size(500, 500)
                            .toFile(smallUploadedFile);
                    uploadedFile.delete();
                    Img img = new Img();
                    img.setPath(smallResultFilename);
                    img.setApartment(apartment);
                    imgRepository.save(img);

                }
                return "redirect:/apartments";
                
            }
            
            model.addAttribute("errors", errors);
            model.addAttribute("rooms", rooms);
            model.addAttribute("square", square);
            model.addAttribute("floor", floor);
            model.addAttribute("address", address);
            model.addAttribute("description", description);
            model.addAttribute("cost", cost);
            model.addAttribute("phone", phone);
            model.addAttribute("city", city);
            model.addAttribute("id", id);
            
            model.addAttribute("cities", cities);
            model.addAttribute("isNew", false);

            return "apartment/add";
            
        
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model,Principal principal) {
        loadCities();
        User user = userRepository.findByEmailAddress(principal.getName());
        Apartment apartment = apartmentRepository.findByIdAndUserId(id,user.getId());
        
        
        if(apartment == null){
            return "redirect:/apartments"; 
        }
        
        model.addAttribute("rooms", apartment.getRooms());
        model.addAttribute("square", apartment.getSquare());
        model.addAttribute("floor", apartment.getFloor());
        model.addAttribute("address", apartment.getAddress());
        model.addAttribute("description", apartment.getDescription());
        model.addAttribute("cost", apartment.getCost());
        model.addAttribute("phone", apartment.getPhone());
        model.addAttribute("city", apartment.getCity());
        model.addAttribute("images", apartment.getImages());

        
        
        model.addAttribute("isNew", false);
        model.addAttribute("cities", cities);
        return "apartment/add";
    }
    
    
    @GetMapping("/apartments")
    public String apartments(Principal principal,Model model) {
        User user = userRepository.findByEmailAddress(principal.getName());
        List<Apartment> apartments = apartmentRepository.findByUserId(user.getId());
        model.addAttribute("apartments", apartments);
      
        return "apartment/apartments";
    }
    
    
    @RequestMapping(value = "/postadd", method = RequestMethod.POST)
    public String postadd(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam int rooms,
            @RequestParam int square,
            @RequestParam int floor,
            @RequestParam int city,
            @RequestParam String address,
            @RequestParam int cost,
            @RequestParam String phone,
            @RequestParam String description,
            Principal principal,
            Model model) throws IOException {
        
        loadCities();
        
        List<String> errors = new ArrayList<String>();
        if(rooms < 1){
            errors.add("Комнат не заполенено");
        }
        if(square < 1){
            errors.add("Площадь не заполенено");
        }
        if(floor < 1){
            errors.add("Этаж не заполенено");
        }
        if(address.length() < 1){
            errors.add("Адрес не заполенено");
        }
        if(cost < 1){
            errors.add("Цена не заполенено");
        }
        if(phone.length() < 1){
            errors.add("Телефон не заполенено");
        }
        
        if(errors.size() == 0){
            User user = userRepository.findByEmailAddress(principal.getName());
            
            Apartment apartment = new Apartment();
            apartment.setRooms(rooms);
            apartment.setSquare(square);
            apartment.setFloor(floor);
            apartment.setAddress(address);
            apartment.setCost(cost);
            apartment.setCity(city);
            apartment.setPhone(phone);
            apartment.setVip(false);
            apartment.setDescription(description);
            long unixTime = System.currentTimeMillis() / 1000L;

            apartment.setCreate_time(unixTime);
            apartment.setUser_id(user.getId());
            apartmentRepository.save(apartment);

            for(MultipartFile file:files){
                String uploadPath = env.getProperty("uploa.path");

                File uploadDir = new File(uploadPath);
                
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                String smallResultFilename = "small_" + uuidFile + "." + file.getOriginalFilename();

                File uploadedFile = new File(uploadPath + "/" + resultFilename);
                File smallUploadedFile = new File(uploadPath + "/" + smallResultFilename);

                file.transferTo(uploadedFile);
                
                Thumbnails.of(uploadedFile)
			.size(500, 500)
			.toFile(smallUploadedFile);
                uploadedFile.delete();
                Img img = new Img();
                img.setPath(smallResultFilename);
                img.setApartment(apartment);
                imgRepository.save(img);
                
            }
            return "redirect:/apartments"; 
            
        }
        model.addAttribute("errors", errors);
        model.addAttribute("rooms", rooms);
        model.addAttribute("square", square);
        model.addAttribute("floor", floor);
        model.addAttribute("address", address);
        model.addAttribute("description", description);
        model.addAttribute("cost", cost);
        model.addAttribute("phone", phone);
        model.addAttribute("city", city);
        model.addAttribute("cities", cities);
        model.addAttribute("isNew", true);

        return "apartment/add";
    }
}
