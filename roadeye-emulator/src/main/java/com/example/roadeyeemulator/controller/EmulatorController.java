package com.example.roadeyeemulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmulatorController {

    @GetMapping
    public String view() {
        return "emulator";
    }
}
