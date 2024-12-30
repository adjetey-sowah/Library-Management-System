package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Queue;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private ResultSet resultSet;

    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(preparedStatement);
        reservationRepository = new ReservationRepository(databaseConfig);
    }

    @Test
    public void testCreateReservation_Success() throws SQLException {
        // Arrange
        Reservations reservation = new Reservations();
        reservation.setUserId(1L);
        reservation.setResourceId(2L);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = reservationRepository.createReservation(reservation);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).setLong(2, 2L);
    }

    @Test
    public void testUpdateReservation_Success() throws SQLException {
        // Arrange
        Long reservationId = 1L;
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = reservationRepository.updateReservation(reservationId);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setString(1, ReservationStatus.COMPLETED.toString());
        verify(preparedStatement).setLong(2, reservationId);
    }

    @Test
    public void testFindAllReservations() throws SQLException {
        // Arrange
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("reservation_id")).thenReturn(1L);
        when(resultSet.getLong("user_id")).thenReturn(1L);
        when(resultSet.getLong("resource_id")).thenReturn(2L);
        when(resultSet.getDate("reservation_date")).thenReturn(Date.valueOf("2024-12-28"));
        when(resultSet.getString("reservation_status")).thenReturn(ReservationStatus.PENDING.toString());

        // Act
        Queue<Reservations> reservations = reservationRepository.findAllReservations();

        // Assert
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
    }
}

