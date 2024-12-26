package com.juls.firstapp.librarymanagementsystem.dao.dto;


import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDTOTest {

    private ReservationDTO reservationDTO;

    @BeforeEach
    void setUp() {
        reservationDTO = new ReservationDTO();
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long reservationId = 1L;
        String username = "JohnDoe";
        String resourceName = "Java Programming Book";
        LocalDateTime reservationDate = LocalDateTime.of(2024, 12, 25, 15, 30);
        LocalDate exceptionDate = LocalDate.of(2025, 1, 1);
        ReservationStatus status = ReservationStatus.COMPLETED;

        // Act
        reservationDTO.setReservationId(reservationId);
        reservationDTO.setUsername(username);
        reservationDTO.setResourceName(resourceName);
        reservationDTO.setReservationDate(reservationDate);
        reservationDTO.setExceptionDate(exceptionDate);
        reservationDTO.setStatus(status);

        // Assert
        assertEquals(reservationId, reservationDTO.getReservationId());
        assertEquals(username, reservationDTO.getUserName());
        assertEquals(resourceName, reservationDTO.getResourceName());
        assertEquals(reservationDate, reservationDTO.getReservationDate());
        assertEquals(exceptionDate, reservationDTO.getExceptionDate());
        assertEquals(status, reservationDTO.getStatus());
    }

    @Test
    void testToString() {
        // Arrange
        reservationDTO.setReservationId(2L);
        reservationDTO.setUsername("JaneDoe");
        reservationDTO.setResourceName("Spring Boot Guide");
        reservationDTO.setReservationDate(LocalDateTime.of(2024, 12, 31, 10, 0));
        reservationDTO.setExceptionDate(LocalDate.of(2025, 2, 15));
        reservationDTO.setStatus(ReservationStatus.PENDING);

        String expectedString = "ReservationDTO{" +
                "reservationId=2, userName=JaneDoe, resourceName=Spring Boot Guide, reservationDate=2024-12-31T10:00, exceptionDate=2025-02-15, status=PENDING}";

        // Act
        String actualString = reservationDTO.toString();

        // Assert
        assertEquals(expectedString, actualString);
    }

    @Test
    void testWithMockito() {
        // Arrange
        ReservationDTO mockDTO = Mockito.mock(ReservationDTO.class);
        Mockito.when(mockDTO.getReservationId()).thenReturn(3L);
        Mockito.when(mockDTO.getUserName()).thenReturn("MockUser");
        Mockito.when(mockDTO.getResourceName()).thenReturn("Mockito Essentials");
        Mockito.when(mockDTO.getReservationDate()).thenReturn(LocalDateTime.of(2024, 12, 24, 12, 0));
        Mockito.when(mockDTO.getExceptionDate()).thenReturn(LocalDate.of(2025, 1, 5));
        Mockito.when(mockDTO.getStatus()).thenReturn(ReservationStatus.PENDING);

        // Act
        Long reservationId = mockDTO.getReservationId();
        String username = mockDTO.getUserName();
        String resourceName = mockDTO.getResourceName();
        LocalDateTime reservationDate = mockDTO.getReservationDate();
        LocalDate exceptionDate = mockDTO.getExceptionDate();
        ReservationStatus status = mockDTO.getStatus();

        // Assert
        assertEquals(3L, reservationId);
        assertEquals("MockUser", username);
        assertEquals("Mockito Essentials", resourceName);
        assertEquals(LocalDateTime.of(2024, 12, 24, 12, 0), reservationDate);
        assertEquals(LocalDate.of(2025, 1, 5), exceptionDate);
        assertEquals(ReservationStatus.PENDING, status);

        // Verify interactions with mock
        Mockito.verify(mockDTO).getReservationId();
        Mockito.verify(mockDTO).getUserName();
        Mockito.verify(mockDTO).getResourceName();
        Mockito.verify(mockDTO).getReservationDate();
        Mockito.verify(mockDTO).getExceptionDate();
        Mockito.verify(mockDTO).getStatus();
    }
}

