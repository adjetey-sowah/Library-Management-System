package com.juls.firstapp.librarymanagementsystem.dao.dto;

import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;

import java.time.LocalDate;

public class ReservationDTO {
    private Long reservationId;
    private String resourceTitle;
    private String userName;
    private LocalDate reservationDate;
    private LocalDate returnDateOfItem;
    private ReservationStatus reservationStatus;


    public ReservationDTO(Long reservationId,
                          String resourceTitle,
                          String userName,
                          LocalDate reservationDate,
                          LocalDate returnDateOfItem,
                          ReservationStatus reservationStatus)
    {
        this.reservationId = reservationId;
        this.resourceTitle = resourceTitle;
        this.userName = userName;
        this.reservationDate = reservationDate;
        this.returnDateOfItem = returnDateOfItem;
        this.reservationStatus = reservationStatus;
    }

    public ReservationDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalDate getReturnDateOfItem() {
        return returnDateOfItem;
    }

    public void setReturnDateOfItem(LocalDate returnDateOfItem) {
        this.returnDateOfItem = returnDateOfItem;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "reservationId=" + reservationId +
                ", resourceTitle='" + resourceTitle + '\'' +
                ", userName='" + userName + '\'' +
                ", reservationDate=" + reservationDate +
                ", returnDateOfItem=" + returnDateOfItem +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
