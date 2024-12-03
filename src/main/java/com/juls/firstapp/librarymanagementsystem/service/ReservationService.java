package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ReservationRepository;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ReservationService {

    private final Mappers mappers;
    private final ReservationRepository reservationRepository;

    public ReservationService() throws SQLException, ClassNotFoundException {
        this.mappers = new Mappers();
        this.reservationRepository = new ReservationRepository();

    }

    public Queue<ReservationDTO> getAllReservations() throws Exception {
        Queue<ReservationDTO> reservationList = new LinkedList<>();
        for (Reservations reservation : reservationRepository.findAllReservations()){
            reservationList.add(mappers.maptoReservation(reservation));
        }
        return reservationList;
    }

    public static void main(String[] args) throws Exception {
        ReservationService service = new ReservationService();

        service.getAllReservations().forEach(System.out::println);
    }
}
