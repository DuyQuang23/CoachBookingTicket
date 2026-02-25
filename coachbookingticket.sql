-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 15, 2025 lúc 09:19 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `coachbookingticket`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `type` enum('CAR','TRIP') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `driver`
--

CREATE TABLE `driver` (
  `driver_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `license_number` varchar(30) DEFAULT NULL,
  `experience_years` int(11) DEFAULT NULL,
  `status` enum('AVAILABLE','BUSY','INACTIVE') DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `driver`
--

INSERT INTO `driver` (`driver_id`, `user_id`, `license_number`, `experience_years`, `status`) VALUES
(1, NULL, 'HN123456', 7, 'AVAILABLE'),
(2, NULL, 'HN654321', 10, 'AVAILABLE'),
(3, NULL, 'HN789012', 5, 'AVAILABLE'),
(4, NULL, 'HN987654', 8, 'AVAILABLE'),
(5, NULL, 'HN111222', 12, 'AVAILABLE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `driverdetail`
--

CREATE TABLE `driverdetail` (
  `driver_detail_id` int(11) NOT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `note` text DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `driverdetail`
--

INSERT INTO `driverdetail` (`driver_detail_id`, `driver_id`, `address`, `date_of_birth`, `note`, `avatar`) VALUES
(1, 1, 'Hà Nội', '1985-04-12', NULL, '/images/drivers/driver1.jpg'),
(2, 2, 'Nam Định', '1980-02-10', NULL, '/images/drivers/driver2.jpg'),
(3, 3, 'Hải Phòng', '1987-07-22', NULL, '/images/drivers/driver3.jpg'),
(4, 4, 'Thanh Hóa', '1983-03-18', NULL, '/images/drivers/driver4.jpg'),
(5, 5, 'Nghệ An', '1978-11-05', NULL, '/images/drivers/driver5.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `hire_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `passengercar`
--

CREATE TABLE `passengercar` (
  `car_id` int(11) NOT NULL,
  `plate_number` varchar(20) NOT NULL,
  `model` varchar(50) DEFAULT NULL,
  `seat_capacity` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE','MAINTENANCE') DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `passengercar`
--

INSERT INTO `passengercar` (`car_id`, `plate_number`, `model`, `seat_capacity`, `driver_id`, `status`) VALUES
(1, '29B-12345', 'Giường nằm', 40, 1, 'ACTIVE'),
(2, '29B-67890', 'Limousine', 18, 1, 'ACTIVE'),
(3, '30A-11223', 'Giường nằm', 40, 2, 'ACTIVE'),
(4, '30A-44556', 'Ghế ngồi', 30, 3, 'ACTIVE'),
(5, '31B-77889', 'Giường nằm', 40, 4, 'ACTIVE'),
(6, '31B-99000', 'Limousine', 18, 4, 'ACTIVE'),
(7, '32C-11122', 'Giường nằm', 40, 5, 'ACTIVE'),
(8, '32C-33344', 'Ghế ngồi', 30, 5, 'ACTIVE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rating`
--

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `route`
--

CREATE TABLE `route` (
  `route_id` int(11) NOT NULL,
  `start_point` varchar(100) DEFAULT NULL,
  `end_point` varchar(100) DEFAULT NULL,
  `distance_km` decimal(6,2) DEFAULT NULL,
  `estimated_duration` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `route`
--

INSERT INTO `route` (`route_id`, `start_point`, `end_point`, `distance_km`, `estimated_duration`) VALUES
(1, 'Hà Nội', 'Hải Phòng', 120.00, '03:00:00'),
(2, 'Hà Nội', 'Nam Định', 90.00, '02:30:00'),
(3, 'Hà Nội', 'Ninh Bình', 95.00, '02:45:00'),
(4, 'Hà Nội', 'Thanh Hóa', 150.00, '04:00:00'),
(5, 'Hà Nội', 'Nghệ An', 300.00, '07:00:00'),
(6, 'Hà Nội', 'Hà Tĩnh', 360.00, '08:00:00'),
(7, 'Hà Nội', 'Đà Nẵng', 760.00, '10:00:00'),
(8, 'Hà Nội', 'Huế', 670.00, '09:30:00'),
(9, 'Hà Nội', 'Quảng Bình', 500.00, '08:00:00'),
(10, 'Hà Nội', 'Quảng Ninh', 160.00, '03:30:00'),
(11, 'Hà Nội', 'Lạng Sơn', 160.00, '03:30:00'),
(12, 'Hà Nội', 'Bắc Giang', 60.00, '02:00:00'),
(13, 'Hà Nội', 'Bắc Ninh', 40.00, '01:30:00'),
(14, 'Hà Nội', 'Vĩnh Phúc', 70.00, '02:15:00'),
(15, 'Hà Nội', 'Phú Thọ', 90.00, '02:30:00'),
(16, 'Hà Nội', 'Yên Bái', 160.00, '04:00:00'),
(17, 'Hà Nội', 'Lào Cai', 300.00, '07:00:00'),
(18, 'Hà Nội', 'Sơn La', 320.00, '07:30:00'),
(19, 'Hà Nội', 'Điện Biên', 470.00, '09:30:00'),
(20, 'Hà Nội', 'Hòa Bình', 80.00, '02:15:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `routestop`
--

CREATE TABLE `routestop` (
  `stop_id` int(11) NOT NULL,
  `route_id` int(11) DEFAULT NULL,
  `stop_name` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `order_index` int(11) DEFAULT NULL,
  `arrival_time` time DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `note` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `routestop`
--

INSERT INTO `routestop` (`stop_id`, `route_id`, `stop_name`, `address`, `order_index`, `arrival_time`, `departure_time`, `note`) VALUES
(1, 1, 'Bến xe Giáp Bát', 'Hà Nội', 1, NULL, '06:00:00', NULL),
(2, 1, 'Bến xe Niệm Nghĩa', 'Hải Phòng', 2, '09:00:00', NULL, NULL),
(3, 2, 'Bến xe Nước Ngầm', 'Hà Nội', 1, NULL, '07:00:00', NULL),
(4, 2, 'TP Nam Định', 'Nam Định', 2, '09:30:00', NULL, NULL),
(5, 3, 'Bến xe Giáp Bát', 'Hà Nội', 1, NULL, '08:00:00', NULL),
(6, 3, 'Bến xe Ninh Bình', 'Ninh Bình', 2, '10:30:00', NULL, NULL),
(7, 4, 'Bến xe Giáp Bát', 'Hà Nội', 1, NULL, '09:00:00', NULL),
(8, 4, 'TP Thanh Hóa', 'Thanh Hóa', 2, '12:30:00', NULL, NULL),
(9, 5, 'Bến xe Nước Ngầm', 'Hà Nội', 1, NULL, '10:00:00', NULL),
(10, 5, 'TP Vinh', 'Nghệ An', 2, '17:00:00', NULL, NULL),
(11, 10, 'Bến xe Mỹ Đình', 'Hà Nội', 1, NULL, '11:00:00', NULL),
(12, 10, 'TP Hạ Long', 'Quảng Ninh', 2, '14:00:00', NULL, NULL),
(13, 17, 'Bến xe Mỹ Đình', 'Hà Nội', 1, NULL, '22:00:00', NULL),
(14, 17, 'TP Lào Cai', 'Lào Cai', 2, '06:00:00', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `seat`
--

CREATE TABLE `seat` (
  `seat_id` int(11) NOT NULL,
  `car_id` int(11) DEFAULT NULL,
  `seat_number` varchar(10) DEFAULT NULL,
  `seat_type` enum('NORMAL','VIP') DEFAULT 'NORMAL',
  `status` enum('AVAILABLE','BOOKED','BROKEN') DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `seat`
--

INSERT INTO `seat` (`seat_id`, `car_id`, `seat_number`, `seat_type`, `status`) VALUES
(1, 1, 'A1', 'NORMAL', 'AVAILABLE'),
(2, 1, 'A2', 'NORMAL', 'AVAILABLE'),
(3, 1, 'A3', 'NORMAL', 'BOOKED'),
(4, 1, 'A4', 'NORMAL', 'AVAILABLE'),
(5, 1, 'B1', 'VIP', 'BOOKED'),
(6, 1, 'B2', 'VIP', 'AVAILABLE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticketdetail`
--

CREATE TABLE `ticketdetail` (
  `ticket_id` int(11) NOT NULL,
  `trip_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `seat_id` int(11) DEFAULT NULL,
  `booking_date` datetime DEFAULT current_timestamp(),
  `payment_status` enum('UNPAID','PAID','REFUNDED') DEFAULT 'UNPAID',
  `qr_code` varchar(255) DEFAULT NULL,
  `cancel_flag` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ticketdetail`
--

INSERT INTO `ticketdetail` (`ticket_id`, `trip_id`, `user_id`, `seat_id`, `booking_date`, `payment_status`, `qr_code`, `cancel_flag`) VALUES
(1, 1, NULL, 3, '2025-10-14 23:15:57', 'PAID', 'QR001', 0),
(2, 1, NULL, 5, '2025-10-14 23:15:57', 'PAID', 'QR002', 0),
(3, 2, NULL, 1, '2025-10-14 23:15:57', 'UNPAID', 'QR003', 0),
(4, 4, NULL, 2, '2025-10-14 23:15:57', 'PAID', 'QR004', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `trip`
--

CREATE TABLE `trip` (
  `trip_id` int(11) NOT NULL,
  `route_id` int(11) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `departure_time` datetime DEFAULT NULL,
  `arrival_time` datetime DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `available_seats` int(11) DEFAULT NULL,
  `status` enum('SCHEDULED','ONGOING','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `trip`
--

INSERT INTO `trip` (`trip_id`, `route_id`, `car_id`, `departure_time`, `arrival_time`, `price`, `available_seats`, `status`) VALUES
(1, 1, 1, '2025-10-20 06:00:00', '2025-10-20 09:00:00', 150000.00, 40, 'SCHEDULED'),
(2, 2, 2, '2025-10-20 07:00:00', '2025-10-20 09:30:00', 120000.00, 18, 'SCHEDULED'),
(3, 3, 3, '2025-10-20 08:00:00', '2025-10-20 10:30:00', 130000.00, 40, 'SCHEDULED'),
(4, 4, 4, '2025-10-20 09:00:00', '2025-10-20 12:30:00', 180000.00, 30, 'SCHEDULED'),
(5, 5, 5, '2025-10-20 10:00:00', '2025-10-20 17:00:00', 250000.00, 40, 'SCHEDULED'),
(6, 7, 6, '2025-10-20 14:00:00', '2025-10-21 05:00:00', 450000.00, 18, 'SCHEDULED'),
(7, 10, 7, '2025-10-20 11:00:00', '2025-10-20 14:00:00', 150000.00, 40, 'SCHEDULED'),
(8, 17, 8, '2025-10-20 22:00:00', '2025-10-21 06:00:00', 250000.00, 30, 'SCHEDULED');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `role` enum('CUSTOMER','DRIVER','EMPLOYEE','ADMIN') DEFAULT 'CUSTOMER',
  `status` tinyint(1) DEFAULT 1,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Chỉ mục cho bảng `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `driverdetail`
--
ALTER TABLE `driverdetail`
  ADD PRIMARY KEY (`driver_detail_id`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `passengercar`
--
ALTER TABLE `passengercar`
  ADD PRIMARY KEY (`car_id`),
  ADD UNIQUE KEY `plate_number` (`plate_number`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Chỉ mục cho bảng `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`rating_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Chỉ mục cho bảng `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`route_id`);

--
-- Chỉ mục cho bảng `routestop`
--
ALTER TABLE `routestop`
  ADD PRIMARY KEY (`stop_id`),
  ADD KEY `route_id` (`route_id`);

--
-- Chỉ mục cho bảng `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`seat_id`),
  ADD KEY `car_id` (`car_id`);

--
-- Chỉ mục cho bảng `ticketdetail`
--
ALTER TABLE `ticketdetail`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `trip_id` (`trip_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `seat_id` (`seat_id`);

--
-- Chỉ mục cho bảng `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`trip_id`),
  ADD KEY `route_id` (`route_id`),
  ADD KEY `car_id` (`car_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `driver`
--
ALTER TABLE `driver`
  MODIFY `driver_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `driverdetail`
--
ALTER TABLE `driverdetail`
  MODIFY `driver_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `passengercar`
--
ALTER TABLE `passengercar`
  MODIFY `car_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `rating`
--
ALTER TABLE `rating`
  MODIFY `rating_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `route`
--
ALTER TABLE `route`
  MODIFY `route_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `routestop`
--
ALTER TABLE `routestop`
  MODIFY `stop_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `seat`
--
ALTER TABLE `seat`
  MODIFY `seat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `ticketdetail`
--
ALTER TABLE `ticketdetail`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `trip`
--
ALTER TABLE `trip`
  MODIFY `trip_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`);

--
-- Các ràng buộc cho bảng `driver`
--
ALTER TABLE `driver`
  ADD CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Các ràng buộc cho bảng `driverdetail`
--
ALTER TABLE `driverdetail`
  ADD CONSTRAINT `driverdetail_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`);

--
-- Các ràng buộc cho bảng `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Các ràng buộc cho bảng `passengercar`
--
ALTER TABLE `passengercar`
  ADD CONSTRAINT `passengercar_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`);

--
-- Các ràng buộc cho bảng `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`);

--
-- Các ràng buộc cho bảng `routestop`
--
ALTER TABLE `routestop`
  ADD CONSTRAINT `routestop_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`);

--
-- Các ràng buộc cho bảng `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `passengercar` (`car_id`);

--
-- Các ràng buộc cho bảng `ticketdetail`
--
ALTER TABLE `ticketdetail`
  ADD CONSTRAINT `ticketdetail_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`),
  ADD CONSTRAINT `ticketdetail_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `ticketdetail_ibfk_3` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`);

--
-- Các ràng buộc cho bảng `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`),
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `passengercar` (`car_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
