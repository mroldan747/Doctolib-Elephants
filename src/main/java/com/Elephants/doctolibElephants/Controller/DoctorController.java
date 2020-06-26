package com.Elephants.doctolibElephants.Controller;
import com.Elephants.doctolibElephants.entity.*;
import com.Elephants.doctolibElephants.model.FollowUpStatus;
import com.Elephants.doctolibElephants.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    @Autowired
    DoctorRepository doctorRepository;

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
            return "redirect:/dashboard/doctor?patientId=" + ordonnance.getPatient().getId() +
                    "&ordonnanceId=" + ordonnanceId + "&doctorId=" + ordonnance.getDoctor().getId() ;
        }
        return "redirect:/ordonnance"+"?ordonnanceId="+ ordonnanceId;
    }

    @GetMapping("/creationOrdonnance")
    public String creationO(@RequestParam Long doctorId, @RequestParam Long patientId) {

        Optional<Doctor> optionalDoctor =  doctorRepository.findById(doctorId);
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        Ordonnance ordonnance = new Ordonnance();
        if (optionalDoctor.isPresent() && optionalPatient.isPresent()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date current = new Date();

            ordonnance.setDate(sdf.format(current));
            ordonnance.setDoctor(optionalDoctor.get());
            ordonnance.setPatient(optionalPatient.get());
            ordonnance = ordonnanceRepository.save(ordonnance);

        }
        return "redirect:/ordonnance?ordonnanceId=" + ordonnance.getId();
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
                                   @RequestParam Long ordonnanceId,
                                   @RequestParam Long doctorId) {
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
        out.addAttribute("doctorId", doctorId);
        return "dashboard_doctor";
    }
}