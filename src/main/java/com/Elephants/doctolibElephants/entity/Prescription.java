package com.Elephants.doctolibElephants.entity;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String drug;

    @NotNull
    @Column(nullable = false)
    private Integer takenDay;

    @NotNull
    @Column(nullable = false)
    private Integer days;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Integer startHours;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startDate;

    @NotNull
    @Column(nullable = false)
    private Integer inter;

    @ManyToOne
    @JoinColumn
    private Ordonnance ordonnance;

    @OneToMany(mappedBy = "prescription")
    private List<FollowUp> followUps = new ArrayList<>();

    public Prescription() {
    }

    public Prescription(String drug, Integer takenDay, Integer days, Integer inter, String comment, Ordonnance ordonnance) {
        this.drug = drug;
        this.takenDay = takenDay;
        this.days = days;
        this.inter = inter;
        this.comment = comment;
        this.ordonnance = ordonnance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public Integer getTakenDay() {
        return takenDay;
    }

    public void setTakenDay(Integer takenDay) {
        this.takenDay = takenDay;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStartHours() {
        return startHours;
    }

    public void setStartHours(Integer startHours) {
        this.startHours = startHours;
    }

    public Integer getInter() {
        return inter;
    }

    public void setInter(Integer inter) {
        this.inter = inter;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    public List<FollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(List<FollowUp> followUps) {
        this.followUps = followUps;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
}
