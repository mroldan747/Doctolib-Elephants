package com.Elephants.doctolibElephants.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    @GetMapping("/dashboard/Patient")
    public String dashboardPatient() {
        return "dashboard_patient";
    }

    @GetMapping("/medicament")
    public String medicament() {
        return "medicament";
    }
}
