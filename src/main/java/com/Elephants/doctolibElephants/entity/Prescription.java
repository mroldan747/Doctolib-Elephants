package com.Elephants.doctolibElephants.entity;

public class Prescription {

    private Long id;
    private String drug;
    private String takenDay;
    private String day;
    private String comment;
    private Integer startHours;

    public Prescription() {
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

    public String getTakenDay() {
        return takenDay;
    }

    public void setTakenDay(String takenDay) {
        this.takenDay = takenDay;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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
}
