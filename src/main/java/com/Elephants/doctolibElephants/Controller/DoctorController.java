package com.Elephants.doctolibElephants.Controller;
import com.Elephants.doctolibElephants.entity.FollowUp;
import com.Elephants.doctolibElephants.entity.Ordonnance;
import com.Elephants.doctolibElephants.entity.Patient;
import com.Elephants.doctolibElephants.entity.Prescription;
import com.Elephants.doctolibElephants.model.FollowUpStatus;
import com.Elephants.doctolibElephants.repository.FollowUpRepository;
import com.Elephants.doctolibElephants.repository.OrdonnanceRepository;
import com.Elephants.doctolibElephants.repository.PatientRepository;
import com.Elephants.doctolibElephants.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@Controller
public class DoctorController {
    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    OrdonnanceRepository ordonnanceRepository;
    @Autowired
    FollowUpRepository followUpRepository;
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
    @GetMapping("/dashboard/doctor")
    public String dashboardDorctor(Model out,
                                   @RequestParam Long patientId,
                                   @RequestParam Long ordonnanceId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            out.addAttribute("patient", patient);
        }
        List<Prescription> prescriptions = prescriptionRepository.findAllByOrdonnanceId(ordonnanceId);
        Map<Prescription, FollowUpStatus> prescriptionStatus = new LinkedHashMap<>();
        for (Prescription prescription : prescriptions) {
            FollowUpStatus followUpStatus = new FollowUpStatus();
            followUpStatus.setGreen(followUpRepository.totalStatus1(prescription.getId()));
            followUpStatus.setOrange(followUpRepository.totalStatus2(prescription.getId()));
            followUpStatus.setRed(followUpRepository.totalStatus3(prescription.getId()));
            followUpStatus.setTotal(followUpRepository.totalFollowUp(prescription.getId()));
            followUpStatus.setTotalPris(followUpStatus.getGreen()+followUpStatus.getOrange()+followUpStatus.getRed());
            followUpStatus.setRestePrendre((prescription.getDays()*prescription.getTakenDay())-followUpStatus.getTotalPris());
            prescriptionStatus.put(prescription, followUpStatus);
        }
        out.addAttribute("prescriptionStatus", prescriptionStatus);
        return "dashboard_doctor";
    }
}