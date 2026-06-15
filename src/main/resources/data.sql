INSERT IGNORE INTO USER
(U_CODE, U_ID, U_PW, U_NICK, U_NAME, U_BIRTH, U_SEX, U_TEL, U_MAIL, U_ASSI, U_GOAL, U_CUT, USER_AUTH)
VALUES
(1, 'demo1', '$2a$10$CwTycUXWue0Thq9StjUM0uJ8VQxYhUoSvE2nXja/q5xihnX4jYy4G', '강남슈터', '김민준', '1998-01-11', 1, '010-1111-1111', 'demo1@footmap.test', 4, 12, 7, 'ROLE_USER'),
(2, 'demo2', '$2a$10$CwTycUXWue0Thq9StjUM0uJ8VQxYhUoSvE2nXja/q5xihnX4jYy4G', '부산패서', '이서연', '1997-05-21', 2, '010-2222-2222', 'demo2@footmap.test', 8, 6, 5, 'ROLE_USER'),
(3, 'demo3', '$2a$10$CwTycUXWue0Thq9StjUM0uJ8VQxYhUoSvE2nXja/q5xihnX4jYy4G', '대구키퍼', '박지훈', '1996-09-09', 1, '010-3333-3333', 'demo3@footmap.test', 2, 4, 11, 'ROLE_USER');

INSERT IGNORE INTO TEAM
(T_CODE, T_IMG, T_NAME, T_STADIUM, T_VIC, T_DRAW, T_LOSE, T_INTRO)
VALUES
(1, NULL, '강남 FC', '강남 풋살파크', 12, 2, 3, '빠른 패스와 압박을 즐기는 팀입니다.'),
(2, NULL, '부산 웨이브', '해운대 풋살장', 9, 4, 4, '즐겁고 매너 있는 경기를 우선합니다.'),
(3, NULL, '대구 러너스', '수성 풋살아레나', 7, 3, 5, '평일 저녁 매치를 자주 진행합니다.');

INSERT IGNORE INTO TEAM_MANAGEMENT
(TM_CODE, U_CODE, T_CODE, TM_ROLE, TM_STATUS)
VALUES
(1, 1, 1, 'LEADER', 'APPROVED'),
(2, 2, 2, 'LEADER', 'APPROVED'),
(3, 3, 3, 'LEADER', 'APPROVED');

INSERT IGNORE INTO FINFO
(F_CODE, F_AREA, F_NAME, F_ADD, F_SOU, F_TEL, F_IMG, F_COST, F_HOURS, F_PARKING, F_SHOWER, F_NOTICE)
VALUES
(1, '서울 강남', '강남 풋살파크', '서울 강남구 테헤란로 100', '40x20m', '02-111-1111', NULL, '평일 80,000원 / 주말 100,000원', '06:00 - 24:00', '건물 지하 주차 2시간 무료', '샤워실 이용 가능', '풋살화 착용 필수, 우천 시 현장 판단'),
(2, '부산 해운대', '해운대 풋살장', '부산 해운대구 해운대로 200', '38x18m', '051-222-2222', NULL, '시간당 70,000원', '07:00 - 23:00', '전용 주차장 20대', '샤워실 없음', '대여 조끼 제공, 공은 별도 준비'),
(3, '대구 수성', '수성 풋살아레나', '대구 수성구 달구벌대로 300', '40x20m', '053-333-3333', NULL, '시간당 75,000원', '06:00 - 23:30', '인근 공영주차장 이용', '샤워실 이용 가능', '예약 시간 10분 전 입장 가능');

UPDATE FINFO
SET F_COST = '평일 80,000원 / 주말 100,000원',
    F_HOURS = '06:00 - 24:00',
    F_PARKING = '건물 지하 주차 2시간 무료',
    F_SHOWER = '샤워실 이용 가능',
    F_NOTICE = '풋살화 착용 필수, 우천 시 현장 판단'
WHERE F_CODE = 1;

UPDATE FINFO
SET F_COST = '시간당 70,000원',
    F_HOURS = '07:00 - 23:00',
    F_PARKING = '전용 주차장 20대',
    F_SHOWER = '샤워실 없음',
    F_NOTICE = '대여 조끼 제공, 공은 별도 준비'
WHERE F_CODE = 2;

UPDATE FINFO
SET F_COST = '시간당 75,000원',
    F_HOURS = '06:00 - 23:30',
    F_PARKING = '인근 공영주차장 이용',
    F_SHOWER = '샤워실 이용 가능',
    F_NOTICE = '예약 시간 10분 전 입장 가능'
WHERE F_CODE = 3;

INSERT IGNORE INTO BOARD
(IDX, U_CODE, B_NICK, B_TITLE, B_CONTENTS, B_CNT, DEL_CHK)
VALUES
(1, 1, '강남슈터', '주말 경기 상대팀 찾습니다', '토요일 오후 강남 풋살파크에서 5:5 경기 가능 팀 구합니다.', 8, 'N'),
(2, 2, '부산패서', '해운대 평일 저녁 매치 모집', '매너 경기 위주로 함께 하실 팀 환영합니다.', 5, 'N'),
(3, 3, '대구키퍼', '골키퍼 용병 가능합니다', '대구 지역 평일 저녁 골키퍼 필요하시면 연락 주세요.', 3, 'N');

INSERT IGNORE INTO GAMELIST
(G_CODE, G_DUNG, G_SEARCH, G_DATE, G_TIME, G_PEO, G_MAGAM, F_CODE, G_INTRO)
VALUES
(1, 1, NULL, DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), '19:00:00', '5:5', 1, 1, '강남 FC 주최 친선 경기입니다.'),
(2, 2, NULL, DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), '20:00:00', '6:6', 1, 2, '부산 웨이브와 즐겁게 경기할 팀을 찾습니다.');
