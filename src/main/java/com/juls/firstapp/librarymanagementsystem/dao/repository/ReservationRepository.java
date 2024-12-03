package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;


import java.sql.*;
import java.time.LocalDateTime;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ReservationRepository {

    private final Connection connection;

    public ReservationRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
    }

    public boolean createReservation(Reservations reservations){
        String sql = "INSERT INTO reservation (user_id, resource_id, reservation_date, reservation_status) VALUES(?,?,?,?)";
        Date reservationDate = Date.valueOf(reservations.getReservationDate().toLocalDate());
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1,reservations.getUserId());
            preparedStatement.setLong(2,reservations.getResourceId());
            preparedStatement.setDate(3,reservationDate);
            preparedStatement.setString(4, reservations.getStatus().toString());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateReservation(Long reservationId){
        String sql = "UPDATE reservation SET reservation_status = ? where reservation_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, ReservationStatus.COMPLETED.toString());
            preparedStatement.setLong(2, reservationId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Could not update reservation: "+e.getMessage());
        }
    }

    public Queue<Reservations> findAllReservations(){
        String sql = "SELECT * FROM reservation";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            Queue<Reservations> reservations = new LinkedList<>();
            while ((resultSet.next())){
                Reservations reservation = new Reservations();
                reservation.setReservationId(resultSet.getLong(1));
                reservation.setUserId(resultSet.getLong("user_id"));
                reservation.setResourceId(resultSet.getLong("resource_id"));
                reservation.setReservationDate(resultSet.getDate("reservation_date").toLocalDate().atStartOfDay());
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("reservation_status")));

                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ReservationRepository repository = new ReservationRepository();
        Queue<Reservations> reservations = repository.findAllReservations();

        Reservations reservation = new Reservations();
        reservation.setUserId(18L);
        reservation.setResourceId(36L);

//        System.out.println(repository.createReservation(reservation));

        reservations.forEach(System.out::println);
    }


}
