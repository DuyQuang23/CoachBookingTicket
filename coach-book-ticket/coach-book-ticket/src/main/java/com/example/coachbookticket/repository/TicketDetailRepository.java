package com.example.coachbookticket.repository;

import com.example.coachbookticket.model.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TicketDetailRepository extends JpaRepository<TicketDetail, Integer> {

    List<TicketDetail> findByUser_UserId(Integer userId);

    List<TicketDetail> findByTrip_TripId(Integer tripId);

    List<TicketDetail> findBySeat_SeatId(Integer seatId);

    List<TicketDetail> findByPaymentStatus(String status);

    // kiểm tra ghế đã được đặt trong chuyến này chưa
    @Query("""
        SELECT COUNT(t) > 0 FROM TicketDetail t 
        WHERE t.trip.tripId = :tripId AND t.seat.seatId = :seatId AND t.cancelFlag = false
    """)
    boolean isSeatBooked(Integer tripId, Integer seatId);


}
