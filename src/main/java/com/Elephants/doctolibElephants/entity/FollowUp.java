package com.Elephants.doctolibElephants.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "follow_up")
public class FollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer hour;

    @NotNull
    @Column(nullable = false, columnDefinition = "int default 3")
    private Integer status;

    @NotNull
    @Column(nullable = false)
    private Integer day;

    @ManyToOne
    @JoinColumn
    private Prescription prescription;

    public FollowUp() {
    }

    public FollowUp(Integer hour, Integer status, Integer day, Prescription prescription) {
        this.hour = hour;
        this.status = status;
        this.day = day;
        this.prescription = prescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
