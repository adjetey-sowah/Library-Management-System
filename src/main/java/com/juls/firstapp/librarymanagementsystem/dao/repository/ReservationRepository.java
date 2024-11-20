package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class ReservationRepository {

    private static final Logger log = LoggerFactory.getLogger(ReservationRepository.class);
    private final Connection connection;
    private final Mappers mappers;

    public ReservationRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
        this.mappers = new Mappers();
    }


    public boolean createReservation(Reservations reservations) throws SQLException {
        String sql = "Insert into Reservation(user_id,resource_id,reservation_date,reservation_status) values (?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,reservations.getUserId());
            preparedStatement.setLong(2,reservations.getResourceId());
            preparedStatement.setDate(3, Date.valueOf(reservations.getReservationDate().toLocalDate()));
            preparedStatement.setString(4,reservations.getStatus().toString());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            connection.rollback();;
            throw new RuntimeException(e);
        }
    }

    public boolean updateReservation(Long reservation_id, ReservationStatus reservationStatus){
        String sql = "Update reservation set status = ? where reservation_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, reservationStatus.toString());
            preparedStatement.setLong(2,reservation_id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Queue<ReservationDTO> getAllReservations(){
        String sql = "{call findAllReservations()}";


        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();
            Queue<ReservationDTO> reservations = new LinkedList<>();

            while (resultSet.next()){
                boolean add = reservations.add(mappers.mapToReservation(resultSet));
            }

            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Reservations reservations = new Reservations();

        reservations.setUserId(11L);
        reservations.setResourceId(29L);
        reservations.setStatus(ReservationStatus.PENDING);

        ReservationRepository repository = new ReservationRepository();

        if (repository.createReservation(reservations)){
            log.info("I have bee added successfully");
        }
        else log.info("I am not added");


        System.out.println(repository.getAllReservations().stream().findFirst());
    }



}
