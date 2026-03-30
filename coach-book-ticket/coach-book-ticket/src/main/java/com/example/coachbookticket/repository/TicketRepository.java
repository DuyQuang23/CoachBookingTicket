package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByUser_UserId(Integer userId);

    List<Ticket> findByTrip_TripId(Integer tripId);

    List<Ticket> findBySeat_SeatId(Integer seatId);

    List<Ticket> findByPaymentStatus(String status);

    // kiểm tra ghế đã được đặt trong chuyến này chưa
    @Query("""
        SELECT COUNT(t) > 0 FROM Ticket t
        WHERE t.trip.tripId = :tripId AND t.seat.seatId = :seatId AND t.cancelFlag = false
   """)
    boolean isSeatBooked(Integer tripId, Integer seatId);

    @Query("SELECT t.seat.seatId FROM Ticket t " +
            "JOIN t.pickupStop p " +
            "JOIN t.dropoffStop d " +
            "WHERE t.trip.tripId = :tripId " +
            "AND t.car.carId = :carId " +
            "AND t.paymentStatus IN ('PAID', 'PENDING') " +
            "AND t.cancelFlag = false " +
            "AND :newPickupOrder < d.stopOrder " +
            "AND :newDropoffOrder > p.stopOrder")
    List<Integer> findBookedSeatIdsForSegment(
            @Param("tripId") Integer tripId,
            @Param("carId") Integer carId,
            @Param("newPickupOrder") Integer newPickupOrder,
            @Param("newDropoffOrder") Integer newDropoffOrder
    );
}
