-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 11, 2020 at 08:24 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `todolist_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `todolist_table`
--

CREATE TABLE `todolist_table` (
  `id_todolist` int(11) NOT NULL,
  `email_user` varchar(50) NOT NULL,
  `title_todolist` varchar(100) NOT NULL,
  `desc_todolist` varchar(200) NOT NULL,
  `date_todolist` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `todolist_table`
--

INSERT INTO `todolist_table` (`id_todolist`, `email_user`, `title_todolist`, `desc_todolist`, `date_todolist`) VALUES
(1, 'tes@gmail.com', 'title tes1', 'des tes1', 'date tes1'),
(2, 'tes@gmail.com', 'asdasd', 'asdasd', 'qwe'),
(3, 'danes@gmail.com', 'danes', 'danes', 'danes'),
(4, 'danes@gmail.com', 'da', 'da', 'da');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `email_user` varchar(50) NOT NULL,
  `name_user` varchar(50) NOT NULL,
  `password_user` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`email_user`, `name_user`, `password_user`) VALUES
('asd@gmail.com', 'asd', 'asd'),
('danes@gmail.com', 'danes', 'danes'),
('denis@gmail.com', 'denis', 'denis'),
('popo@gmail.com', 'popo', 'popo'),
('tes@gmail.com', 'tes', 'tes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `todolist_table`
--
ALTER TABLE `todolist_table`
  ADD PRIMARY KEY (`id_todolist`),
  ADD KEY `email_user` (`email_user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `todolist_table`
--
ALTER TABLE `todolist_table`
  MODIFY `id_todolist` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `todolist_table`
--
ALTER TABLE `todolist_table`
  ADD CONSTRAINT `todolist_table_ibfk_1` FOREIGN KEY (`email_user`) REFERENCES `user` (`email_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
