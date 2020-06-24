package com.Elephants.doctolibElephants.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorController {

    @GetMapping("/ordonnance")
    public String ordonnance(){

        return "ordonnance";
    }

    @GetMapping("/dashboard/doctor")
    public String dashboardDoctor() {

        return "dashboard_doctor";
    }
}
