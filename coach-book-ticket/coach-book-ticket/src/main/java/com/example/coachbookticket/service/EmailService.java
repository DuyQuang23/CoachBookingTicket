package com.example.coachbookticket.service;

import com.example.coachbookticket.model.Ticket;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j // Dùng để ghi log
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendTicketConfirmation(String toEmail, Ticket ticket) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("CoachBooking <email-cua-ban@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject("Xác nhận đặt vé thành công - Mã vé: #" + ticket.getTicketId());

            // Format tiền và thời gian cho đẹp
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedPrice = currencyFormat.format(ticket.getPrice());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
            String bookingTime = ticket.getBookingDate().format(timeFormatter);

            // Nội dung HTML chuyên nghiệp
            String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;'>" +
                    "<div style='background-color: #007bff; color: white; padding: 20px; text-align: center;'>" +
                    "<h2>Cảm ơn bạn đã đặt vé!</h2>" +
                    "</div>" +
                    "<div style='padding: 20px;'>" +
                    "<p>Xin chào <strong>" + ticket.getUser().getFullName() + "</strong>,</p>" +
                    "<p>Hệ thống đã ghi nhận thông tin đặt vé của bạn. Dưới đây là thông tin chi tiết:</p>" +
                    "<table style='width: 100%; border-collapse: collapse; margin-top: 20px;'>" +
                    "<tr style='background-color: #f8f9fa;'><td style='padding: 10px; border: 1px solid #ddd;'><strong>Chuyến xe:</strong></td><td style='padding: 10px; border: 1px solid #ddd;'>" + ticket.getTrip().getRoute().getStartPoint() + " - " + ticket.getTrip().getRoute().getEndPoint() + "</td></tr>" +
                    "<tr><td style='padding: 10px; border: 1px solid #ddd;'><strong>Vị trí ghế:</strong></td><td style='padding: 10px; border: 1px solid #ddd; color: #d9534f; font-weight: bold;'>" + ticket.getSeat().getSeatNumber() + "</td></tr>" +
                    "<tr style='background-color: #f8f9fa;'><td style='padding: 10px; border: 1px solid #ddd;'><strong>Điểm lên:</strong></td><td style='padding: 10px; border: 1px solid #ddd;'>" + ticket.getPickupStop().getLocationName() + "</td></tr>" +
                    "<tr><td style='padding: 10px; border: 1px solid #ddd;'><strong>Điểm xuống:</strong></td><td style='padding: 10px; border: 1px solid #ddd;'>" + ticket.getDropoffStop().getLocationName() + "</td></tr>" +
                    "<tr style='background-color: #f8f9fa;'><td style='padding: 10px; border: 1px solid #ddd;'><strong>Thời gian đặt:</strong></td><td style='padding: 10px; border: 1px solid #ddd;'>" + bookingTime + "</td></tr>" +
                    "<tr><td style='padding: 10px; border: 1px solid #ddd;'><strong>Tổng tiền:</strong></td><td style='padding: 10px; border: 1px solid #ddd; font-weight: bold;'>" + formattedPrice + "</td></tr>" +
                    "</table>" +
                    "<p style='margin-top: 20px;'>Vui lòng có mặt tại điểm đón trước 15 phút. Chúc bạn có một chuyến đi vui vẻ!</p>" +
                    "</div>" +
                    "</div>";

            helper.setText(htmlContent, true); // true để bật chế độ HTML

            mailSender.send(message);
            log.info("Email xác nhận đã được gửi thành công đến: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Lỗi khi gửi email xác nhận vé: ", e);
            // Bắt lỗi ở đây để ứng dụng không bị crash nếu cấu hình sai hoặc rớt mạng
        }
    }
}