package com.Elephants.doctolibElephants.Controller;

import com.Elephants.doctolibElephants.entity.FollowUp;
import com.Elephants.doctolibElephants.entity.Ordonnance;
import com.Elephants.doctolibElephants.entity.Prescription;
import com.Elephants.doctolibElephants.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PatientController {


    @Autowired
    OrderRepository orderRepository;


    @GetMapping("/dashboard/patient")
    public String dashboardPatient() {
        return "dashboard_patient";
    }

    @GetMapping("/medicament")
    public String medicament(Model model, @RequestParam Long med, @RequestParam Long id) {
        Optional<Ordonnance> optionalOrdonnance = orderRepository.findByPatientId(id);
        if (optionalOrdonnance.isPresent()) {
            Prescription prescription = optionalOrdonnance.get().getPrescriptions().stream()
                    .filter(item -> item.getId().equals(med))
                    .collect(Collectors.toList()).get(0);

            if (prescription.getStartHours() == null) {
                model.addAttribute("isStart", false);
            } else {
                model.addAttribute("isStart", true);
                List<FollowUp> followUps = prescription.getFollowUps();
                Map<FollowUp, String> followUpsMap = new LinkedHashMap<>();
                for (FollowUp followUp : followUps) {
                    if (followUp.getStatus() == 3) {
                        followUpsMap.put(followUp, "red");
                    } else if (followUp.getStatus() == 2) {
                        followUpsMap.put(followUp, "orange");
                    } else {
                        followUpsMap.put(followUp, "green");
                    }
                }
                model.addAttribute("followUps", followUpsMap);
            }

        } else {
            model.addAttribute("isStart", false);
        }
        model.addAttribute("med", med);
        model.addAttribute("id", id);
        return "medicament";
    }

    @PostMapping("/medicament")
    public String startHour(@RequestParam Long med, @RequestParam Long id, @RequestParam Integer hour) {
        Optional<Ordonnance> optionalOrdonnance = orderRepository.findByPatientId(id);
        if (optionalOrdonnance.isPresent()) {
            Prescription prescription = optionalOrdonnance.get().getPrescriptions().stream()
                    .filter(item -> item.getId().equals(med))
                    .collect(Collectors.toList()).get(0);
            prescription.setStartHours(hour);
        }

        return "redirect:/medicament?med=" + med + "&" + "id=" + id;
    }

}
