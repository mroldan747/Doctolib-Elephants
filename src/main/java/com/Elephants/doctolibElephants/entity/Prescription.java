package com.Elephants.doctolibElephants.entity;

public class Prescription {

    private Long id;
    private String drug;
    private Integer takenDay;
    private Integer days;
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
}
