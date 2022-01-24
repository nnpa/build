/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controllers;

import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ivan
 */
@Controller
public class ApartmentController {
    
    @GetMapping("/add")
    public String registration(Model model) {
        HashMap<Integer, String> cities = new HashMap<>();
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
        
        model.addAttribute("city", 1);
        
        model.addAttribute("cities", cities);
        return "apartment/add";
    }
}
