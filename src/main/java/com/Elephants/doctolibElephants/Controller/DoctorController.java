package com.Elephants.doctolibElephants.Controller;

import com.Elephants.doctolibElephants.entity.Ordonnance;
import com.Elephants.doctolibElephants.entity.Patient;
import com.Elephants.doctolibElephants.entity.Prescription;
import com.Elephants.doctolibElephants.repository.OrdonnanceRepository;
import com.Elephants.doctolibElephants.repository.PatientRepository;
import com.Elephants.doctolibElephants.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DoctorController {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    OrdonnanceRepository ordonnanceRepository;


    @GetMapping("/ordonnance")
    public String presciption(Model out,
                              @RequestParam Long ordonnanceId){

        Optional<Ordonnance> optionalOrdonnance = ordonnanceRepository.findById(ordonnanceId);
        if (optionalOrdonnance.isPresent()) {
            Ordonnance ordonnance = optionalOrdonnance.get();
            out.addAttribute("ordonnance", ordonnance);
        }

        Prescription prescription = new Prescription();
        out.addAttribute("prescriptions", prescriptionRepository.findAllByOrdonnanceId(ordonnanceId));

        return "ordonnance";
    }

    @PostMapping("/ordonnance")
    public String postForm(Model out,
                           @RequestParam String drug,
                           @RequestParam Integer takenDay,
                           @RequestParam Integer days,
                           @RequestParam Integer inter,
                           @RequestParam String comment,
                           @RequestParam Long ordonnanceId,
                           @RequestParam Integer creation) {

        Optional<Ordonnance> optionalOrdonnance = ordonnanceRepository.findById(ordonnanceId);
        Ordonnance ordonnance = new Ordonnance();
        if (optionalOrdonnance.isPresent()) {
            ordonnance = optionalOrdonnance.get();
        }

        Prescription prescription = new Prescription(drug, takenDay, days, inter, comment, ordonnance);
        out.addAttribute("presciption", prescriptionRepository.save(prescription));
        if (creation == 1) {
            return "redirect:/";
        }
        return "redirect:/ordonnance"+"?ordonnanceId="+ ordonnanceId;

    }

    /* changer direction page*/
    @GetMapping("/")
    @ResponseBody
    public String show() {

        return "hello";
    }

    /*@GetMapping("/dashboard/doctor")
      public Patient dashboardDoctor(Model out,
                                    @RequestParam Long patientId) {

        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            out.addAttribute("patient", patient);
        }

        return ;
    }*/


}
