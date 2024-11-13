package com.juls.firstapp.librarymanagementsystem.model;

import java.time.LocalDateTime;

public class Reservations {
    private Long reservationId;
    private Long userId;
    private String resourceId;
    private String status;
    private LocalDateTime reservationDate;


    public Reservations(){
        reservationDate = LocalDateTime.now();
    }

    public Reservations(Long userId, String resourceId){
        this.userId = userId;
        this.resourceId = resourceId;
        reservationDate = LocalDateTime.now();
    }


}
