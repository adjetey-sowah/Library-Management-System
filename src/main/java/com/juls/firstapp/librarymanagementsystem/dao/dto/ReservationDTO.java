package com.juls.firstapp.librarymanagementsystem.dao.dto;

import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDTO {

    private Long reservationId;
    private String username;
    private String resourceName;
    private LocalDateTime reservationDate;
    private LocalDate exceptionDate;
    private ReservationStatus status;

    public ReservationDTO(){

    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(LocalDate exceptionDate) {
        this.exceptionDate = exceptionDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "reservationId=" + reservationId +
                ", userName=" + username +
                ", resourceName=" + resourceName +
                ", reservationDate=" + reservationDate +
                ", exceptionDate=" + exceptionDate +
                ", status=" + status +
                '}';
    }
}
