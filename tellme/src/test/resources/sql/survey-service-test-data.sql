-- user
INSERT INTO user (id, created, deleted, updated, social_type, nickname, email, password, picture) VALUES (3, '2023-08-04 00:02:39.000028', null, '2023-08-04 00:02:39.000081', 'KAKAO', '김태영', null, 'NONE', 'http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg');
INSERT INTO user (id, created, deleted, updated, social_type, nickname, email, password, picture) VALUES (4, '2023-08-04 00:02:39.000028', null, '2023-08-04 00:02:39.000081', 'KAKAO', '김태영1', null, 'NONE', 'http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg');

-- survey
INSERT INTO survey (id, created, deleted, updated, title, content, image) VALUES (1, '2023-08-03 23:57:40', null, null, '유형테스트', '유형테스트 - MBTI', null);

-- question
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (1, 1, '2023-08-03 23:57:40', null, null, '시원한 집에서 혼자 넷플릭스', '시원한 영화관에서 친구들과 영화', '무더운 여름, 휴일을 보내는 모습은?', '459', '128');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (2, 2, '2023-08-03 23:57:41', null, null, '무슨 큰일이 있으려고 그러지? 미리 걱정하기', '역시 난 운이 좋아! 단순하게 생각하기', '이상할 정도로 운이 좋은 날, 드는 생각은?', '1458', '29');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (3, 3, '2023-08-03 23:57:42', null, null, '갑자기? 적응 안돼… 스트레스 가득', '분위기 전환되고 좋은데? 에너지 가득', '하루아침에 일하는 환경이 바뀌었을 때의 모습은?', '5189', '24');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (4, 4, '2023-08-03 23:57:43', null, null, '아무도 없으면 내가 해야겠지? 자원하기', '조용히 하고 있으면 누군가 하겠지… 침묵하기', '팀 프로젝트 리더를 정해야 하는데 아무도 나서지 않을 때의 모습은?', '8', '12459');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (5, 5, '2023-08-03 23:57:44', null, null, '이건 이래서 안 되지 않을까? 분석가', '좋아! 이런건 어때? 아이디어 뱅크', '회의에서 아이디어를 논의하는 모습은?', '15', '2489');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (6, 6, '2023-08-03 23:57:44', null, null, '꾹 참고 조용히 넘어가기', '참을 수 없어!! 끝까지 싸우기', '다수로부터 억울한 상황에 쳐했을 때의 대처 방법은?', '12459', '8');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (7, 7, '2023-08-03 23:57:45', null, null, '일할 의욕이 불타오른다! 몰입하기', '휴가 때 뭐하지? 휴가 계획부터 짜기', '이번 일만 끝나면 보상휴가, 이 소식을 들은 모습은?', '1458', '29');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (8, 8, '2023-08-03 23:57:48', null, null, '나를 위한 무대다! 경쟁을 즐기기', '벌써 스트레스! 마지못해 하기', '이번 발표는 경쟁 PT, 경쟁에 임하는 모습은?', '158', '249');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (9, 9, '2023-08-03 23:57:49', null, null, '누가 알아줬으면 좋겠어! 주변에 알리기', '좋은게 좋은거지~ 혼자만 간직하고 넘어가기', '혼자만의 작은 기여로 팀이 큰 보상을 얻게 되었을 때의 모습은?', '1248', '59');
INSERT INTO question (id, order_number, created, deleted, updated, answer_a, answer_b, question, answer_a_result, answer_b_result) VALUES (10, 10, '2023-08-03 23:57:49', null, null, '한참 동안 비교하고 고민하다 고르기', '직감이 이끄는 선택지 바로 고르기', '앞에 두 가지 선택지가 있을 때의 평소 모습은?', '245', '189');

-- survey_question
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (1, 1, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (2, 2, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (3, 3, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (4, 4, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (5, 5, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (6, 6, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (7, 7, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (8, 8, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (9, 9, 1, null, null, null);
INSERT INTO survey_question (id, question_id, survey_id, created, deleted, updated) VALUES (10, 10, 1, null, null, null);

-- survey_result
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (1, '2023-08-06 02:42:36', null, null, '포레스트 검프는 순수하고 선량한 마음을 가진 캐릭터에요. 언제나 세상을 솔직하고 진심으로 대하고, 다른 사람들을 배려하죠. 자신의 능력과 신념에 충실하면서 어려움을 극복해 나가는 용기와 결단력을 가지고 있는 강한 캐릭터에요.', '포레스트', 2, 1);
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (2, '2023-08-06 02:42:37', null, null, '라라랜드의 남자 주인공 세바스찬은 음악에 대한 열정이 넘치는 캐릭터에요. 피아니스트로서 재즈 음악에 대한 끝없는 열망과 사랑을 가지고 자신만의 꿈을 쫓아요. 현실에 타협하지 않고 감정에 충실하며, 자유로운 정신으로 음악적인 표현을 하는 캐릭터에요.', '세바스찬', 4, 1);
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (3, '2023-08-06 02:42:37', null, null, '닥터 스트레인지는 높은 지능과 탐구적인 면모를 가진 신경외과 의사에요. 마법과 신비한 차원을 탐구하는 것을 즐기며, 항상 새로운 지식과 흥미로운 사실들을 발견하려고 노력해요. 항상 논리적이고 분석적으로 생각하고 나아가, 자신의 능력을 계속 발전시키고, 실패를 통해 더 나은 방향을 찾는 성장형 캐릭터에요.', '닥터 스트레인지', 5, 1);
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (4, '2023-08-06 02:42:38', null, null, '아라곤은 호빗들과 인간들, 엘프들과 드워프들을 이끄는 결단력 있는 캐릭터에요. 지도자적인 자질을 바탕으로 높은 용기를 갖추고, 어려운 상황에서도 주저하지 않고 힘을 발휘해요. 대지의 운명을 바꾸는 큰 비전과 목표를 가지고, 자신의 비전을 위해 강인한 신념을 가지고 나아가죠. 뛰어난 카리스마와 리더십으로 사람들의 신뢰와 존경을 얻는 캐릭터에요.', '아라곤', 8, 1);
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (5, '2023-08-06 02:42:39', null, null, '뉴트 스캐맨더는 신비한 동물 학자로서 자연과 동물의 조화로운 관계를 추구하는 캐릭터에요. 다양한 동물들과 사람들을 이해하고 존중하는 능력으로 각자의 독특한 성격과 특징을 이해하려고 노력하며, 적절한 대응과 조화를 이루는 방법을 찾아요. 친절하고 부드러운 성격으로 주변 사람들에게 포용과 편안함을 주는 캐릭터에요.', '뉴트 스캐맨더', 9, 1);
INSERT INTO survey_result (id, created, deleted, updated, content, type, type_number, survey_id) VALUES (6, '2023-08-06 02:42:40', null, null, '잭 스패로우는 독특한 성격을 지닌 자유로운 영혼의 캐릭터에요. 자신의 규칙에 따라 살고자 하며, 권위와 규칙에 대한 반항적이에요. 전통적인 규칙과 기존의 질서를 거부하고, 자신만의 방식으로 사는 것을 선호하죠. 불가사의한 상황에서도 유머와 센스로 주변 사람들과 우호적인 관계를 형성하며, 신뢰를 쌓는 독특하고 마력적인 캐릭터에요.', '잭 스패로우', 1, 1);

-- survey_result_keyword
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (2, '2023-08-06 02:52:22', null, null, '순수', 1);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (3, '2023-08-06 02:52:23', null, null, '선량', 1);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (4, '2023-08-06 02:52:24', null, null, '믿음', 1);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (20, '2023-08-06 02:56:10', null, null, '열정', 2);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (21, '2023-08-06 02:56:12', null, null, '꿈', 2);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (22, '2023-08-06 02:56:12', null, null, '자유', 2);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (24, '2023-08-06 02:56:42', null, null, '탐구', 3);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (25, '2023-08-06 02:56:43', null, null, '지적', 3);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (26, '2023-08-06 02:56:44', null, null, '성장', 3);
INSERT INTO survey_result_keyword (id, created, deleted, updated, title, survey_result_id) VALUES (27, '2023-08-06 02:56:45', null, null, '용기', 4);

-- survey_completion
INSERT INTO survey_completion (create_user_id, id, survey_id, created, deleted, updated, unique_id) VALUES (4, 5, 1, '2023-08-06 04:32:36.071682', null, '2023-08-06 04:32:36.071739', '4');
INSERT INTO survey_completion (create_user_id, id, survey_id, created, deleted, updated, unique_id) VALUES (4, 7, 1, '2023-08-06 15:24:09.893958', null, '2023-08-06 15:24:09.894015', '2DE3C7B5DA297B03CAFB4F0FF42FF3D3');


-- survey_answer
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 41, 1, 5, '2023-08-06 04:32:36.104666', null, '2023-08-06 04:32:36.104689');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 42, 2, 5, '2023-08-06 04:32:36.114222', null, '2023-08-06 04:32:36.114239');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 43, 3, 5, '2023-08-06 04:32:36.126256', null, '2023-08-06 04:32:36.126277');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 45, 5, 5, '2023-08-06 04:32:36.146339', null, '2023-08-06 04:32:36.146357');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 44, 4, 5, '2023-08-06 04:32:36.132985', null, '2023-08-06 04:32:36.133000');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 46, 6, 5, '2023-08-06 04:32:36.149628', null, '2023-08-06 04:32:36.149641');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 47, 7, 5, '2023-08-06 04:32:36.153206', null, '2023-08-06 04:32:36.153225');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 48, 8, 5, '2023-08-06 04:32:36.161561', null, '2023-08-06 04:32:36.161590');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 49, 9, 5, '2023-08-06 04:32:36.165726', null, '2023-08-06 04:32:36.165751');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 50, 10, 5, '2023-08-06 04:32:36.170607', null, '2023-08-06 04:32:36.170623');

INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('B', 61, 1, 7, '2023-08-06 15:24:09.941517', null, '2023-08-06 15:24:09.941538');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('B', 62, 2, 7, '2023-08-06 15:24:09.957804', null, '2023-08-06 15:24:09.957820');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('B', 63, 3, 7, '2023-08-06 15:24:09.969103', null, '2023-08-06 15:24:09.969120');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 64, 4, 7, '2023-08-06 15:24:09.984590', null, '2023-08-06 15:24:09.984612');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 65, 5, 7, '2023-08-06 15:24:09.990198', null, '2023-08-06 15:24:09.990216');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 66, 6, 7, '2023-08-06 15:24:10.003765', null, '2023-08-06 15:24:10.003780');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 67, 7, 7, '2023-08-06 15:24:10.008061', null, '2023-08-06 15:24:10.008078');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 68, 8, 7, '2023-08-06 15:24:10.020053', null, '2023-08-06 15:24:10.020075');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 69, 9, 7, '2023-08-06 15:24:10.027502', null, '2023-08-06 15:24:10.027521');
INSERT INTO survey_answer (answer, id, question_id, survey_completion_id, created, deleted, updated) VALUES ('A', 70, 10, 7, '2023-08-06 15:24:10.039945', null, '2023-08-06 15:24:10.039964');

-- survey_short_url
INSERT INTO survey_short_url (id, survey_id, user_id, created, deleted, updated, url) VALUES (1, 1, 4, '2023-08-06 02:01:18.532118', null, '2023-08-06 02:01:18.532171', 'MQ==');
