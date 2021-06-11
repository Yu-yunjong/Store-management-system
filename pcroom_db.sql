-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-06-12 00:59
-- 서버 버전: 10.5.9-MariaDB-1:10.5.9+maria~focal
-- PHP 버전: 7.4.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `pcroom_db`
--
CREATE DATABASE IF NOT EXISTS `pcroom_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `pcroom_db`;

-- --------------------------------------------------------

--
-- 테이블 구조 `pc상태`
--

DROP TABLE IF EXISTS `pc상태`;
CREATE TABLE IF NOT EXISTS `pc상태` (
  `PC번호` varchar(5) NOT NULL,
  `PC상태` int(11) NOT NULL,
  `메모` varchar(200) DEFAULT NULL,
  `사용자ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PC번호`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `pc상태`
--

INSERT INTO `pc상태` (`PC번호`, `PC상태`, `메모`, `사용자ID`) VALUES
('1', 1, '', ''),
('10', 1, '', ''),
('11', 1, '', ''),
('12', 1, '', ''),
('13', 1, '', ''),
('14', 1, '', ''),
('15', 1, '', ''),
('16', 1, '', ''),
('17', 1, '', ''),
('18', 0, '키보드 불량', ''),
('19', 1, '', ''),
('2', 1, '', ''),
('20', 1, '', ''),
('3', 0, '업데이트 필요!!', ''),
('4', 0, '그래픽카드 고장', ''),
('5', 1, '', ''),
('6', 1, '', ''),
('7', 1, '', ''),
('8', 1, '', ''),
('9', 1, '', '');

-- --------------------------------------------------------

--
-- 테이블 구조 `다음시리얼`
--

DROP TABLE IF EXISTS `다음시리얼`;
CREATE TABLE IF NOT EXISTS `다음시리얼` (
  `항목` varchar(15) NOT NULL,
  `시리얼` int(11) DEFAULT NULL,
  PRIMARY KEY (`항목`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `다음시리얼`
--

INSERT INTO `다음시리얼` (`항목`, `시리얼`) VALUES
('주문번호', 31);

-- --------------------------------------------------------

--
-- 테이블 구조 `상품`
--

DROP TABLE IF EXISTS `상품`;
CREATE TABLE IF NOT EXISTS `상품` (
  `분류` varchar(10) NOT NULL,
  `상품코드` varchar(10) NOT NULL,
  `상품명` varchar(30) NOT NULL,
  `수량` int(11) DEFAULT 1,
  `단가` int(11) NOT NULL,
  `유통기한` date DEFAULT NULL,
  PRIMARY KEY (`상품코드`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `상품`
--

INSERT INTO `상품` (`분류`, `상품코드`, `상품명`, `수량`, `단가`, `유통기한`) VALUES
('캔음료', 'C01', '코카콜라', 23, 1500, '2022-12-05'),
('이용권', 'CU1', '이용권(30분)', NULL, 500, NULL),
('이용권', 'CU2', '이용권(1시간)', NULL, 1000, NULL),
('이용권', 'CU3', '이용권(2시간)', NULL, 2000, NULL),
('이용권', 'CU4', '이용권(3시간)', NULL, 3000, NULL),
('이용권', 'CU5', '이용권(6시간)', NULL, 5500, NULL),
('이용권', 'CU6', '이용권(11시간) + 콜라 1컵', NULL, 10000, NULL),
('이용권', 'CU7', '이용권(13시간) + 아이스티 1컵', NULL, 12000, NULL),
('라면', 'N01', '신라면', 100, 3000, '2021-09-12'),
('페트음료', 'P01', '칠성 사이다', 50, 2000, '2022-11-22');

-- --------------------------------------------------------

--
-- 테이블 구조 `시간`
--

DROP TABLE IF EXISTS `시간`;
CREATE TABLE IF NOT EXISTS `시간` (
  `회원아이디` varchar(20) NOT NULL,
  `남은시간` int(11) NOT NULL,
  PRIMARY KEY (`회원아이디`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `시간`
--

INSERT INTO `시간` (`회원아이디`, `남은시간`) VALUES
('ak123', 1120),
('anow31', 0),
('hanshin', 12670),
('news2001', 0),
('qwerty123', 21840),
('test', 58789),
('testuser', 0),
('tiger1234', 7380),
('user1212', 17380),
('yuna123', 2280);

-- --------------------------------------------------------

--
-- 테이블 구조 `주문`
--

DROP TABLE IF EXISTS `주문`;
CREATE TABLE IF NOT EXISTS `주문` (
  `주문번호` varchar(15) NOT NULL,
  `상품코드` varchar(10) NOT NULL,
  `회원아이디` varchar(20) NOT NULL,
  `PC번호` varchar(5) DEFAULT NULL,
  `주문시간` datetime DEFAULT current_timestamp(),
  `주문수량` int(11) NOT NULL,
  `금액` int(11) NOT NULL,
  `결제방식` int(11) NOT NULL,
  `처리현황` varchar(20) DEFAULT '주문',
  `메모` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`주문번호`),
  KEY `상품코드` (`상품코드`),
  KEY `회원아이디` (`회원아이디`),
  KEY `주문_ibfk_3_idx` (`PC번호`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `주문`
--

INSERT INTO `주문` (`주문번호`, `상품코드`, `회원아이디`, `PC번호`, `주문시간`, `주문수량`, `금액`, `결제방식`, `처리현황`, `메모`) VALUES
('o1', 'CU7', 'ak123', '5', '2020-10-04 11:55:22', 1, 12000, 1, '처리완료', NULL),
('o10', 'C01', 'anow31', '14', '2021-01-17 17:23:34', 2, 3000, 1, '처리완료', '빨대 2개 부탁드립니다.'),
('o11', 'CU5', 'ak123', '19', '2021-01-18 12:23:34', 1, 5500, 1, '처리완료', NULL),
('o12', 'CU4', 'hanshin', '18', '2021-01-19 12:42:34', 1, 3000, 0, '처리완료', NULL),
('o13', 'N01', 'hanshin', '18', '2021-01-19 12:43:34', 1, 3000, 0, '처리완료', NULL),
('o14', 'P01', 'news2001', '8', '2021-01-21 14:43:34', 3, 6000, 0, '처리완료', NULL),
('o15', 'P01', 'qwerty123', '6', '2021-02-04 14:43:34', 2, 4000, 1, '처리완료', NULL),
('o16', 'CU7', 'tiger1234', '3', '2021-02-06 14:41:34', 1, 12000, 1, '처리완료', NULL),
('o17', 'P01', 'tiger1234', '3', '2021-02-06 14:45:34', 1, 2000, 1, '처리완료', NULL),
('o18', 'CU7', 'yuna123', '13', '2021-02-11 13:24:34', 1, 12000, 1, '처리완료', NULL),
('o19', 'CU4', 'ak123', '12', '2021-02-13 15:21:34', 1, 3000, 0, '처리완료', NULL),
('o2', 'C01', 'ak123', '3', '2020-10-04 11:59:21', 2, 3000, 0, '처리완료', NULL),
('o20', 'N01', 'anow31', '3', '2021-03-12 12:21:34', 1, 3000, 0, '처리완료', '살짝 덜익혀주세요!'),
('o21', 'P01', 'hanshin', '11', '2021-03-15 13:21:11', 2, 4000, 1, '처리완료', NULL),
('o22', 'CU5', 'news2001', '11', '2021-03-18 13:23:11', 1, 5500, 1, '처리완료', NULL),
('o23', 'CU5', 'qwerty123', '7', '2021-04-16 13:23:11', 1, 5500, 1, '처리완료', NULL),
('o24', 'N01', 'tiger1234', '18', '2021-04-18 13:22:11', 2, 6000, 1, '처리완료', '라면 하나는 19번자리에 놓아주세요.'),
('o25', 'N01', 'tiger1234', '18', '2021-04-22 11:22:11', 1, 3000, 0, '처리완료', NULL),
('o26', 'CU7', 'news2001', '11', '2021-05-11 11:22:11', 1, 12000, 1, '처리완료', NULL),
('o27', 'P01', 'yuna123', '14', '2021-05-11 11:22:11', 4, 8000, 1, '처리완료', NULL),
('o28', 'CU7', 'test', '6', '2021-06-04 11:24:08', 1, 12000, 0, '준비중', '아이스티 1컵 부탁드립니다!'),
('o29', 'P01', 'test', '6', '2021-06-04 11:28:07', 2, 4000, 1, '주문', ''),
('o3', 'CU3', 'user1212', '9', '2020-10-04 12:34:56', 1, 2000, 1, '처리완료', NULL),
('o30', 'CU7', 'test', '6', '2021-06-04 18:45:34', 1, 12000, 0, '처리완료', ''),
('o4', 'C01', 'hanshin', '6', '2020-10-11 12:13:14', 2, 3000, 0, '처리완료', '빨대 2개 주실수 있나요?'),
('o5', 'N01', 'tiger1234', '2', '2021-01-11 13:14:15', 1, 3000, 1, '처리완료', '꼬들면(살짝 덜 익혀)으로 부탁드립니다~'),
('o6', 'CU6', 'yuna123', '15', '2021-01-11 14:15:16', 1, 10000, 1, '처리완료', ''),
('o7', 'P01', 'tiger1234', '2', '2021-01-12 11:22:33', 2, 4000, 0, '처리완료', NULL),
('o8', 'CU6', 'yuna123', '15', '2021-01-14 12:23:34', 1, 10000, 0, '처리완료', '콜라에 얼음 넣어주세요'),
('o9', 'C01', 'hanshin', '11', '2021-01-14 15:23:34', 1, 1500, 1, '처리완료', NULL);

-- --------------------------------------------------------

--
-- 테이블 구조 `회원`
--

DROP TABLE IF EXISTS `회원`;
CREATE TABLE IF NOT EXISTS `회원` (
  `아이디` varchar(20) NOT NULL,
  `이름` varchar(10) NOT NULL,
  `비밀번호` varchar(100) NOT NULL,
  `회원등급` int(11) DEFAULT 1,
  `휴대폰` varchar(15) NOT NULL,
  `생년월일` date NOT NULL,
  `이메일` varchar(50) DEFAULT NULL,
  `회원가입일자` datetime DEFAULT current_timestamp(),
  `최근로그인일자` datetime DEFAULT NULL,
  PRIMARY KEY (`아이디`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 테이블의 덤프 데이터 `회원`
--

INSERT INTO `회원` (`아이디`, `이름`, `비밀번호`, `회원등급`, `휴대폰`, `생년월일`, `이메일`, `회원가입일자`, `최근로그인일자`) VALUES
('ak123', '임형태', 'eaf5ae544eb7b3aa44a8159f7e4e5daf5724dae5363950de4d1bbb1e3112c7f0', 1, '010-9421-0111', '1995-11-01', 'ak123@nate.com', '2020-05-22 11:12:13', NULL),
('anow31', '김나영', '2a05945cef4f3049f9b5803241b1f66529e432f5b0bfc316ef986ca83ddc3069', 1, '010-3344-0101', '2002-09-16', 'anow31@naver.com', '2021-04-09 04:41:33', NULL),
('hanshin', '황영택', '3af80424fe1ea8db002f4a0c2757a8f3e0abed24294bb92a7afe9548fcd54976', 1, '010-2222-3333', '1999-04-12', 'hanshin12@hs.ac.kr', '2020-10-11 11:12:41', NULL),
('news2001', '김병만', 'd015b3bda7580345807c8a8672e714ebbc215d9daab6f1c904bb08b504e7cc86', 1, '010-2323-2323', '1999-02-20', 'news2001@naver.com', '2021-01-22 14:55:11', NULL),
('qwerty123', '홍성민', 'daaad6e5604e8e17bd9f108d91e26afe6281dac8fda0091040a7a6d7bd9b43b5', 1, '010-3344-5577', '1998-12-31', 'qwerty123@naver.com', '2021-01-18 22:12:14', NULL),
('test', '테스트', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 1, '010-5555-5555', '2000-11-11', 'test@test.com', '2020-02-02 02:02:02', '2021-06-10 03:32:26'),
('testuser', '테스트', 'ae5deb822e0d71992900471a7199d0d95b8e7c9d05c40a8245a281fd2c1d6684', 1, '010-0000-0000', '1989-05-14', 'test@test.com', '2021-04-23 11:03:33', NULL),
('tiger1234', '이한빛', 'd0bca66914300674771d32ee68b53521ad69d63e59567a1ea2002c56a445e4c8', 1, '010-1234-5678', '1998-12-12', 'tiger1234@hs.ac.kr', '2021-01-11 13:11:43', NULL),
('user1212', '사용자', '9046192edc052a0bacd8cc5da456e030ab2d4dae8e1883aac233bed8df54ef7c', 1, '010-2323-4545', '1997-12-21', 'user1212@nate.com', '2020-04-20 21:33:12', NULL),
('yuna123', '김연아', '7949e66d16c8172f04624e141cb35030385a59b429aa9bf4e7b12b0b1f48af4a', 1, '010-2233-2233', '1998-01-23', 'yuna12@naver.com', '2020-07-12 09:42:10', NULL);

--
-- 트리거 `회원`
--
DROP TRIGGER IF EXISTS `insert_user`;
DELIMITER $$
CREATE TRIGGER `insert_user` AFTER INSERT ON `회원` FOR EACH ROW BEGIN 
		DECLARE id VARCHAR(20);
        
        SET id = NEW.아이디;
        
        INSERT INTO 시간 VALUE(id, '0');
    END
$$
DELIMITER ;

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `시간`
--
ALTER TABLE `시간`
  ADD CONSTRAINT `시간_ibfk_1` FOREIGN KEY (`회원아이디`) REFERENCES `회원` (`아이디`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `주문`
--
ALTER TABLE `주문`
  ADD CONSTRAINT `주문_ibfk_1` FOREIGN KEY (`상품코드`) REFERENCES `상품` (`상품코드`) ON UPDATE CASCADE,
  ADD CONSTRAINT `주문_ibfk_2` FOREIGN KEY (`회원아이디`) REFERENCES `회원` (`아이디`) ON UPDATE CASCADE,
  ADD CONSTRAINT `주문_ibfk_3` FOREIGN KEY (`PC번호`) REFERENCES `pc상태` (`PC번호`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
