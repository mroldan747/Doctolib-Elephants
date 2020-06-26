package com.Elephants.doctolibElephants.Controller;

import com.Elephants.doctolibElephants.entity.FollowUp;
import com.Elephants.doctolibElephants.entity.Prescription;
import com.Elephants.doctolibElephants.repository.FollowUpRepository;
import com.Elephants.doctolibElephants.repository.OrdonnanceRepository;
import com.Elephants.doctolibElephants.repository.PatientRepository;
import com.Elephants.doctolibElephants.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PatientController {
    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    FollowUpRepository followUpRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    OrdonnanceRepository ordonnanceRepository;

    @GetMapping("/medicament")
    public String medicament(Model model, @RequestParam Long med, @RequestParam Long id) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(med);
        if (optionalPrescription.isPresent()) {
            Prescription prescription = optionalPrescription.get();
            if (prescription.getStartHours() == null) {
                model.addAttribute("isStart", false);
            } else {
                model.addAttribute("isStart", true);
                Calendar now = Calendar.getInstance();
                int day = now.get(Calendar.DAY_OF_YEAR);
                Calendar startDate = prescription.getStartDate();
                int startDay = startDate.get(Calendar.DAY_OF_YEAR);
                List<FollowUp> followUps = prescription.getFollowUps().stream()
                        .filter(item -> {
                            if (startDay - day == 0) {
                                return item.getDay().equals(1);
                            }
                            return item.getDay().equals(day - startDay);
                        })
                        .collect(Collectors.toList());
                Map<FollowUp, String> followUpsMap = new LinkedHashMap<>();
                for (FollowUp followUp : followUps) {
                    if (followUp.getStatus() == 3) {
                        followUpsMap.put(followUp, "red");
                    } else if (followUp.getStatus() == 2) {
                        followUpsMap.put(followUp, "orange");
                    } else if (followUp.getStatus() == 1) {
                        followUpsMap.put(followUp, "green");
                    } else {
                        followUpsMap.put(followUp, "");
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
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(med);
        if (optionalPrescription.isPresent()) {
            Prescription prescription = optionalPrescription.get();
            prescription.setStartHours(hour);
            Calendar current = Calendar.getInstance();
            prescription.setStartDate(current);
            prescriptionRepository.save(prescription);
            // Define next follow ups
            Integer inter = prescription.getInter();
            Integer days = prescription.getDays();
            Integer takenPerDay = prescription.getTakenDay();
            for (int i = 1; i <= days; i++) {
                int take = 0;
                Integer hourUpdated = hour;
                while (take <= takenPerDay && hourUpdated < 22) {
                    int status = 0;
                    if (i == 1 && hourUpdated.equals(hour)) {
                        status = 1;
                    }
                    FollowUp followUp = new FollowUp(hourUpdated, status, i, prescription);
                    followUpRepository.save(followUp);
                    hourUpdated += inter;
                    take++;
                }
            }
        }
        return "redirect:/medicament?med=" + med + "&" + "id=" + id;
    }

    @GetMapping("/dashboard/patient")
    public String showDrugList(Model out, @RequestParam Long id) {
        List<Prescription> prescriptionsList = prescriptionRepository.findByPatientId(id);
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_YEAR);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        Map<Prescription, FollowUp> prescriptions = new HashMap<>();
        for (Prescription prescription : prescriptionsList) {
            if (prescription.getStartHours() == null) {
                FollowUp startFollowUp = new FollowUp();
                startFollowUp.setStatus(5);
                prescriptions.put(prescription, startFollowUp);
            } else {
                int startDay = prescription.getStartDate().get(Calendar.DAY_OF_YEAR);
                Integer followUpDay = day - startDay == 0 ? 1 : day - startDay;
                List<FollowUp> followUp = followUpRepository.findAllByPrescriptionIdAndDay(prescription.getId(), followUpDay);
                List<FollowUp> followUpBefore = followUp.stream()
                        .filter(item -> item.getHour() >= hour - 1 && item.getHour() <= hour &&
                                !item.getStatus().equals(1) && !item.getStatus().equals(2))
                        .collect(Collectors.toList());
                FollowUp followUpAfter = new FollowUp();
                int diff = 12;
                for (FollowUp followUp1 : followUp) {
                    if (followUp1.getHour() - hour > 0 && followUp1.getHour() - hour < diff
                            && !followUp1.getStatus().equals(1) && !followUp1.getStatus().equals(2)) {
                        diff = followUp1.getHour() - hour;
                        followUpAfter = followUp1;
                    }
                }
                if (followUpBefore.size() != 0) {
                    prescriptions.put(prescription, followUpBefore.get(0));
                } else if (followUpAfter.getId() != null) {
                    prescriptions.put(prescription, followUpAfter);
                }
            }
        }
        out.addAttribute("prescriptions", prescriptions);
        out.addAttribute("idPatient", id);
        return "dashboard_patient";
    }

    @GetMapping("/prise")
    public String prise(@RequestParam Integer prise, @RequestParam Long id, @RequestParam Long idPatient) {
        Optional<FollowUp> optionalFollowUp = followUpRepository.findById(id);
        if (optionalFollowUp.isPresent()) {
            FollowUp followUp = optionalFollowUp.get();
            followUp.setStatus(prise);
            followUpRepository.save(followUp);
        }
        return "redirect:/dashboard/patient" + "?id=" + idPatient;
    }

    @GetMapping("/patient")
    public String userMedicament(Model out,
                                 @RequestParam Long id) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(id);
        out.addAttribute("prescriptions", prescriptions);
        out.addAttribute("idPatient", id);
        return "user_medicaments";
    }
}