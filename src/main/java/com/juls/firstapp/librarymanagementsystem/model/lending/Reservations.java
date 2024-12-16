package com.juls.firstapp.librarymanagementsystem.model.lending;

import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;

import java.time.LocalDateTime;

public class Reservations {
    private Long reservationId;
    private Long userId;
    private Long resourceId;
    private ReservationStatus status;
    private LocalDateTime reservationDate;


    public Reservations(){

        reservationDate = LocalDateTime.now();
        status = ReservationStatus.PENDING;

    }

    public Reservations(Long userId, Long resourceId){
        this.userId = userId;
        this.resourceId = resourceId;
        reservationDate = LocalDateTime.now();
        status = ReservationStatus.PENDING;
    }



    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "reservationId=" + reservationId +
                ", userId=" + userId +
                ", resourceId='" + resourceId + '\'' +
                ", status=" + status +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
