-- Database 생성
DROP DATABASE factory;
CREATE DATABASE factory;

-- 1) USERS 테이블
DROP TABLE factory.users;
CREATE TABLE factory.users
(
   user_email varchar(255) NOT NULL
   , user_role varchar(20) NOT NULL DEFAULT 'ROLE_USER' -- 'ROLE_USER', 'ROLE_ADMIN', 'ROLE_AGENT'
   , user_phone varchar(50) NOT NULL
   , user_name varchar(100)
   , sns_type varchar(50) NOT NULL
   , created_time datetime NOT NULL DEFAULT current_timestamp
   , update_time datetime
   , provider_id varchar(100) NOT NULL 
      , CONSTRAINT user_email_PK PRIMARY KEY(user_email)
      , CONSTRAINT user_role_CHECK check(user_role IN ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_AGENT'))
);
COMMIT;
SELECT * FROM factory.users u ;

-- 2) AGENT 테이블
DROP TABLE factory.agent;
CREATE TABLE factory.agent
(
   seq_no INT AUTO_INCREMENT
   , agent_email varchar(255) NOT NULL
   , enroll varchar(300)
   , file varchar(500)
      , CONSTRAINT seq_no_PK PRIMARY KEY(seq_no)
      , CONSTRAINT agent_email_FK FOREIGN KEY(agent_email) REFERENCES factory.users(user_email)
);
COMMIT;
SELECT * FROM factory.agent a;

-- 3) SURVEY 테이블
DROP TABLE factory.survey;
CREATE TABLE factory.survey
(
   id int AUTO_INCREMENT
   , user_email varchar(255) NOT NULL
   , province_input varchar(300)
   , highway_input int NOT NULL
   , port_input int NOT NULL
   , train_input int NOT NULL
   , airport_input int NOT NULL
   , price_input int NOT NULL
   , industry_input decimal(2) NOT NULL
   , products_input varchar(100) NOT NULL
   , raw_materials_input varchar(100) NOT NULL
      , CONSTRAINT survey_id_PK PRIMARY KEY(id)
      , CONSTRAINT survey_user_email_FK FOREIGN KEY(user_email) REFERENCES factory.users(user_email)
);
COMMIT;
SELECT * FROM factory.survey s;

-- 4) COMPLEX_BASIC_PK 테이블
DROP TABLE factory.complex_basic_pk;
CREATE TABLE factory.complex_basic_pk
(
   complex_name VARCHAR(200) NOT NULL
   , province VARCHAR(50)
   , land_price INT
   , complex_group VARCHAR(20) NOT NULL
      , CONSTRAINT complex_basic_pk_complex_name_PK PRIMARY KEY(complex_name)
      
);
COMMIT;
SELECT * FROM factory.complex_basic_pk b;

-- 4-2) COMPLEX_BASIC_FACTORY
DROP TABLE factory.complex_basic_factory;
CREATE TABLE factory.complex_basic_factory
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name VARCHAR(200) NOT NULL
   , address VARCHAR(200)
   , main_industry INT
      , CONSTRAINT complex_basic_factory_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.complex_basic_factory b;

-- factory.complex_basic_factory의 complex_name 중복값 지우기 
DELETE b1
FROM factory.complex_basic_factory b1
JOIN factory.complex_basic_factory b2 
  ON b1.complex_name = b2.complex_name 
  AND b1.id > b2.id;


-- 5) COMPLEX_INFO 테이블
DROP TABLE factory.complex_info;
CREATE TABLE factory.complex_info
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name varchar(200)
   , status VARCHAR(10)
   , completion_date VARCHAR(20)
   , avg_price INT
   , industry_type VARCHAR(500)
   , management VARCHAR(200)
   , land_size INT
   , complex_group VARCHAR(20) NOT NULL
   , website_url VARCHAR(255)
         , CONSTRAINT complex_info_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.complex_info i;


-- 6) COMPLEX_RATIO 테이블
DROP TABLE factory.complex_ratio;
CREATE TABLE factory.complex_ratio(
   id INT AUTO_INCREMENT,
   complex_name VARCHAR(200) NOT NULL,
   food DECIMAL(9,6),
   clothing DECIMAL(9,6),
   wood DECIMAL(9,6),
   petrochemical DECIMAL(9,6),
   non_metal DECIMAL(9,6),
   metal DECIMAL(9,6),
   machinery DECIMAL(9,6),
   electronics DECIMAL(9,6),
   trans DECIMAL(9,6),
   other DECIMAL(9,6),
   non_manu DECIMAL(9,6),
   complex_group VARCHAR(20) NOT NULL 
		 , CONSTRAINT complex_ratio_id_PK PRIMARY KEY(id)
         , CONSTRAINT complex_ratio_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.complex_ratio;

-- 7) COMPLEX_TRANS
DROP TABLE factory.complex_trans;
CREATE TABLE factory.complex_trans
(
   id INT AUTO_INCREMENT
   , complex_name VARCHAR(200) NOT NULL
   , highway_score DECIMAL(2,1) NOT NULL
   , port_score DECIMAL(2,1) NOT NULL
   , train_score DECIMAL(2,1) NOT NULL
   , airport_score DECIMAL(2,1) NOT NULL
   , final_score DECIMAL(2,1) NOT NULL
   , complex_group VARCHAR(20)NOT NULL
   	, CONSTRAINT complex_trans_id_PK PRIMARY KEY(id)
    , CONSTRAINT complex_trans_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.complex_trans t; 

-- 8) BOARD 테이블
DROP TABLE factory.board;
CREATE TABLE factory.board
( 
   board_id INT AUTO_INCREMENT
   , user_email VARCHAR(255) NOT NULL
   , board_title VARCHAR(255) NOT NULL
   , board_contents TEXT
   , category VARCHAR(100)
   , created_time DATETIME DEFAULT current_timestamp
   , update_time DATETIME
      , CONSTRAINT board_id_PK PRIMARY KEY(board_id)
      , CONSTRAINT board_id_user_email_FK FOREIGN KEY(user_email) REFERENCES factory.users(user_email)
);
COMMIT;
SELECT * FROM factory.board b; 

-- 9) SURVEY_RESULT 테이블
DROP TABLE factory.survey_result;
CREATE TABLE factory.survey_result
(
   user_email VARCHAR(255) NOT NULL
   , id INT AUTO_INCREMENT
   , complex_name VARCHAR(200) NOT NULL
   , region_score DECIMAL(9,6)
   , industry_score DECIMAL(9,6)
   , land_price_score DECIMAL(9,6)
   , raw_material_score DECIMAL(9,6)
   , product_score DECIMAL(9,6)
   , transport_score DECIMAL(9,6)
   , worker_score DECIMAL(9,6)
   , create_time datetime
   , rank_result INT NOT NULL
   , final_similarity_score DECIMAL(9,6)
      , CONSTRAINT survey_result_PK PRIMARY KEY(id)
      , CONSTRAINT survey_result_email_FK FOREIGN KEY(user_email) REFERENCES factory.users(user_email)
);
COMMIT;
SELECT * FROM factory.survey_result r;

-- 10) LIKE_LIST 테이블
DROP TABLE factory.like_list;
CREATE TABLE factory.like_list
(
   user_email VARCHAR(255) NOT NULL
   , id INT AUTO_INCREMENT
   , complex_name VARCHAR(200) NOT NULL
   , created_like_time DATETIME DEFAULT current_timestamp
      , CONSTRAINT like_list_id_PK PRIMARY KEY(id)
      , CONSTRAINT like_list_user_email_FK FOREIGN KEY(user_email) REFERENCES factory.users(user_email)
);
COMMIT;
SELECT * FROM factory.like_list l;

-- 11) INTEGRATION 테이블

DROP TABLE factory.integration;
CREATE TABLE factory.integration
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name VARCHAR(200) NOT NULL
   , food DECIMAL(7,6) NOT NULL
   , clothing DECIMAL(7,6) NOT NULL
   , wood DECIMAL(7,6) NOT NULL
   , petrochemical DECIMAL(7,6) NOT NULL
   , non_metal DECIMAL(7,6) NOT NULL
   , metal DECIMAL(7,6) NOT NULL
   , machinery DECIMAL(7,6) NOT NULL
   , electronics DECIMAL(7,6) NOT NULL
   , trans DECIMAL(7,6) NOT NULL
   , other DECIMAL(7,6) NOT NULL
   , non_manu DECIMAL(7,6) NOT NULL
   	, CONSTRAINT integration_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.integration i;

-- 12) 노동력 테이블
DROP TABLE factory.manpower;
CREATE TABLE factory.manpower
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name VARCHAR(200) NOT NULL
   , manpower DECIMAL(6,5) NOT NULL
   	, CONSTRAINT manpower_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.manpower i;



-- 13) TRANS_INFO 테이블
DROP TABLE factory.trans_info;
CREATE TABLE factory.trans_info
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name   VARCHAR(200) NOT NULL
   , complex_group   varchar(200)
   , airport_name varchar(200)
   , airport_distance varchar(20)
   , seaport_name varchar(200)
   , seaport_distance varchar(20)
   , highway_name varchar(200)
   , highway_distance varchar(20)
   , station_name varchar(200)
   , station_distance varchar(20)
      , CONSTRAINT trans_info_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
ALTER TABLE factory.trans_info
ADD CONSTRAINT trans_info_complex_name_unique UNIQUE (complex_name);

COMMIT;
SELECT * FROM factory.trans_info t;


-- 15)  complex_position 테이블
DROP TABLE factory.complex_position;
CREATE TABLE factory.complex_position
(
	id int AUTO_INCREMENT PRIMARY KEY 
   , complex_name   VARCHAR(200) NOT NULL
   , latitude VARCHAR(100) NOT NULL
   , longitude VARCHAR(100) NOT NULL
      , CONSTRAINT complex_position_complex_name_FK FOREIGN KEY(complex_name) REFERENCES factory.complex_basic_pk(complex_name)
);
COMMIT;
SELECT * FROM factory.complex_position t;

-- 테이블 생성 끝.
 




-- 테스트용 유저 생성
INSERT INTO factory.users (user_email, user_role, user_phone, user_name, sns_type, created_time, provider_id)
VALUES ('test@example.com', 'ROLE_USER', '000-0000-0000', 'Test User', 'test', NOW(), 'test_provider');
COMMIT;
SELECT * FROM factory.users u;

SELECT * FROM factory.survey_result sr ;

-- 트랜잭션 잠금 보유 확인
SHOW ENGINE INNODB STATUS;

-- 현재 실행중인 트랜잭션 확인
SHOW FULL PROCESSLIST;

SELECT * FROM factory.users WHERE user_email = 'test@example.com';

SELECT * FROM factory.users u ;

-- 링크 10개 바꾸기
UPDATE factory.complex_info
SET website_url = 'https://www.kicox.or.kr/index.do'
WHERE complex_name = '남동국가산업단지';

UPDATE factory.complex_info
SET website_url = 'https://www.kicox.or.kr/index.do'
WHERE complex_name = '시화국가산업단지';

UPDATE factory.complex_info
SET website_url = 'https://www.kicox.or.kr/index.do'
WHERE complex_name = '반월국가산업단지';

UPDATE factory.complex_info
SET website_url = 'https://www.ih.co.kr/main/land/landDetail.do?land_seq=61&landDiv=1140'
WHERE complex_name = '인천서부일반산업단지';

UPDATE factory.complex_info
SET website_url = 'https://www.kicox.or.kr/index.do'
WHERE complex_name = '한국수출산업(부평)국가산업단지';

UPDATE factory.complex_info
SET website_url = 'https://www.geumcheon.go.kr/portal/contents.do?key=914'
WHERE complex_name = '서울디지털국가산업단지';

UPDATE factory.complex_info
SET website_url = 'http://m.picm.or.kr/complex/?p_url=complex_5&B_Name=complex&b_dir=talkclub&b_url=list_l&category=complex_5_1_10'
WHERE complex_name = '평택송탄일반산업단지';

UPDATE factory.complex_info
SET website_url = 'https://hsidc.hsuco.or.kr/lmth/02_industry/industry01_03.asp'
WHERE complex_name = '화성발안일반산업단지';

UPDATE factory.complex_info
SET website_url = 'http://m.picm.or.kr/complex/?dir_in=&p_url=complex_5&B_Name=complex&b_dir=talkclub&b_url=list_l&category=complex_5_2_10'
WHERE complex_name = '평택일반산업단지';

UPDATE factory.complex_info
SET website_url = 'http://m.picm.or.kr/complex/?dir_in=&p_url=complex_5&B_Name=complex&b_dir=talkclub&b_url=list_l&category=complex_5_3_10'
WHERE complex_name = '평택칠괴일반산업단지';


COMMIT;

-- 발표 전 초기화. 찜 목록과 추천 결과 기록 테이블 비우기

TRUNCATE TABLE factory.like_list;
TRUNCATE TABLE factory.survey_result;

SELECT * FROM factory.like_list ll ;
SELECT * FROM factory.survey_result sr ;


-- 유저 테이블을 날리고 싶은 경우 순서대로 실행. (FK제약 무시하고 없애기)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE factory.users;
SET FOREIGN_KEY_CHECKS = 1;
COMMIT;