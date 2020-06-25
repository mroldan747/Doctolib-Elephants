package com.Elephants.doctolibElephants.Controller;

import com.Elephants.doctolibElephants.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/dashboard/Patient")
    public String dashboardPatient() {
        return "dashboard_patient";
    }

    @GetMapping("/medicament")
    public String medicament() {
        return "medicament";
    }
}
