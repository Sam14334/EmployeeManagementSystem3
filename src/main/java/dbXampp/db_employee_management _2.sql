-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2026 at 11:59 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_employee_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `dept_id` int(11) NOT NULL,
  `dept_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`dept_id`, `dept_name`) VALUES
(1, 'Management'),
(2, 'Operations'),
(3, 'Technical'),
(4, 'Finance'),
(5, 'Logistics');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `employee_id` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `dept_id` int(11) DEFAULT 2,
  `role` varchar(50) DEFAULT 'HR Staff',
  `status_id` int(11) DEFAULT 3,
  `salary` double(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`employee_id`, `username`, `password`, `first_name`, `last_name`, `email`, `phone_number`, `dept_id`, `role`, `status_id`, `salary`) VALUES
('1001', 'gian_karlo', 'admin123', 'Gian Karlo', 'Alatiit', 'karlo@staffsync.com', '09171234567', 1, 'Manager', 1, 65000.00),
('1002', 'jomar_p', 'hrpass321', 'Jomar', 'Pangilinan', 'jomar@staffsync.com', '09187654321', 2, 'HR Staff', 1, 45000.00),
('1003', 'rich_jasper', 'tech99', 'Rich Jasper', 'Federio', 'rich@staffsync.com', '09223334444', 3, 'Employee', 1, 38000.00),
('1004', 'admin', '123', 'Karlo', 'Alatiit', 'karloalatiit111@gmail.com', '09978158181', 1, 'HR Staff', 1, 100000.00),
('1005', 'emp', '123', 'Kurt', 'Redondo', 'kurtredondo@gmail.com', '0923232323', 2, 'Employee', 3, 232323.00),
('1006', 'manager', '123', 'Gian', 'Lacao', 'gianlacao@gmail.com', '0932323323', 2, 'Manager', 2, 232323.00),
('1007', 'hr', '123', 'sam', 'sam', 'sam@sam.com', '09923841342', 2, 'HR Staff', 3, 40000.00),
('1011', 'micheal', '123', 'micheal', 'samia', 'michealsamia@gmail.com', '09923134121', 2, 'HR Staff', 3, 12333.00);

-- --------------------------------------------------------

--
-- Table structure for table `employee_reviews`
--

CREATE TABLE `employee_reviews` (
  `review_id` int(11) NOT NULL,
  `employee_id` varchar(50) NOT NULL,
  `behavior` decimal(10,0) NOT NULL,
  `communication` decimal(10,0) NOT NULL,
  `management` decimal(10,0) NOT NULL,
  `development` decimal(10,0) NOT NULL,
  `details` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee_reviews`
--

INSERT INTO `employee_reviews` (`review_id`, `employee_id`, `behavior`, `communication`, `management`, `development`, `details`) VALUES
(1, '1003', 5, 5, 4, 3, 'review hera tehaphhpuuabapdfs'),
(2, '1003', 2, 3, 2, 1, ''),
(3, '1011', 4, 5, 4, 2, 'great communication skills'),
(4, '1011', 5, 5, 5, 5, 'By mine own reckoning, thou hast borne thy charge with commendable diligence and steadfast resolve. Thy labours hath been of great worth, and thy conduct most seemly. In all matters entruste'),
(5, '1011', 5, 5, 5, 4, 'By all accounts, thou hast performed thy charge with steadfast resolve. Thy labours hath been of good merit, and thy bearing commendable throughout.'),
(6, '1011', 4, 4, 4, 4, 'By all accounts, thou hast performed thy charge with steadfast resolve. Thy labours hath been of good merit, and thy bearing commendable throughout.'),
(7, '1011', 3, 5, 4, 4, 'Thy service hath been marked by diligence and constancy. In every task set before thee, thou hast shown thyself a worthy and dependable servant.'),
(8, '1011', 4, 5, 5, 5, 'In the execution of thy duties, thou hast shown commendable diligence. Thy conduct hath remained proper, and thy efforts worthy of praise.'),
(9, '1011', 4, 3, 2, 2, 'Thy labours hath been carried forth with constancy and care. Thou hast rendered faithful service and fulfilled thy charge with distinction.'),
(10, '1011', 3, 3, 5, 5, 'With unwavering purpose, thou hast pursued thy duties. Thy service hath been dependable, and thy contributions of worthy account.');

-- --------------------------------------------------------

--
-- Table structure for table `employment_statuses`
--

CREATE TABLE `employment_statuses` (
  `status_id` int(11) NOT NULL,
  `status_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employment_statuses`
--

INSERT INTO `employment_statuses` (`status_id`, `status_name`) VALUES
(1, 'Regular'),
(2, 'Probationary'),
(3, 'Contractual');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`dept_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employee_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `fk_employee_department` (`dept_id`),
  ADD KEY `fk_employee_status` (`status_id`);

--
-- Indexes for table `employee_reviews`
--
ALTER TABLE `employee_reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `employment_statuses`
--
ALTER TABLE `employment_statuses`
  ADD PRIMARY KEY (`status_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `employee_reviews`
--
ALTER TABLE `employee_reviews`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `employment_statuses`
--
ALTER TABLE `employment_statuses`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `fk_employee_department` FOREIGN KEY (`dept_id`) REFERENCES `departments` (`dept_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_employee_status` FOREIGN KEY (`status_id`) REFERENCES `employment_statuses` (`status_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `employee_reviews`
--
ALTER TABLE `employee_reviews`
  ADD CONSTRAINT `employee_reviews_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
