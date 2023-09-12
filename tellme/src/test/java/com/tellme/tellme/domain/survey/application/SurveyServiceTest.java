package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.domain.auth.application.KakaoOauth;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.infrastructure.UserJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static com.tellme.tellme.domain.survey.presentation.SurveyDto.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/survey-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class SurveyServiceTest {

    @MockBean
    private KakaoOauth kakaoOauth;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private HttpServletRequest http;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void 최초_설문지_작성하면_short_url_생성() {
        // given
        int userId = 3;
        int createUserId = 3;
        int surveyId = 1;
        String uniqueId = "session1";

        User authenticationUser = userJpaRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원 입니다.")
        );

        List<AnswerContent> list = new ArrayList<>();
        int i = 1;
        while (i <= 10) {
            AnswerContent answerContent = AnswerContent.builder()
                    .question(1)
                    .answer('A')
                    .build();
            list.add(answerContent);
            i++;
        }

        Answer answer = Answer.builder()
                .answerContentList(list)
                .build();

        // when
        SurveyResultInfo survey = surveyService.saveAnswer(surveyId, createUserId, answer, authenticationUser, uniqueId);

        // then
        assertThat(survey.getShortUrl()).isNotNull();
    }

    @Test
    void 설문지_작성시_답변수가_부족하면_에러_발생() {
        // given
        int userId = 3;
        int createUserId = 3;
        int surveyId = 1;
        String uniqueId = "session1";

        User authenticationUser = userJpaRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원 입니다.")
        );

        List<AnswerContent> list = new ArrayList<>();
        int i = 1;
        while (i <= 9) {
            AnswerContent answerContent = AnswerContent.builder()
                    .question(1)
                    .answer('A')
                    .build();
            list.add(answerContent);
            i++;
        }

        Answer answer = Answer.builder()
                .answerContentList(list)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            surveyService.saveAnswer(surveyId, createUserId, answer, authenticationUser, uniqueId);
        }).isInstanceOf(BaseException.class);
    }

    @Test
    void 설문지를_만든사람이_아닌사람은_설문_참여를_여러번_한_경우_에러_발생() {
        // given
        int createUserId = 4;
        int surveyId = 1;
        String uniqueId = "2DE3C7B5DA297B03CAFB4F0FF42FF3D3";

        User authenticationUser = null;

        List<AnswerContent> list = new ArrayList<>();
        int i = 1;
        while (i <= 10) {
            AnswerContent answerContent = AnswerContent.builder()
                    .question(1)
                    .answer('A')
                    .build();
            list.add(answerContent);
            i++;
        }

        Answer answer = Answer.builder()
                .answerContentList(list)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            surveyService.saveAnswer(surveyId, createUserId, answer, authenticationUser, uniqueId);
        }).isInstanceOf(BaseException.class);
    }

    @Test
    void shortUrl로_설문_아이디_user_id_조회() {
        // given
        String shortUrl = "MQ==";

        // when
        SurveyInfo surveyInfo = surveyService.shortUrlDecoding(shortUrl);

        // then
        assertThat(surveyInfo.getSurveyId()).isEqualTo(1);
        assertThat(surveyInfo.getUserId()).isEqualTo(4);
    }

    @Test
    void shortUrl이_존재하지_않을경우_에러_발생() {
        // given
        String shortUrl = "MQ==1";

        // when
        // then
        assertThatThrownBy(() -> {
            surveyService.shortUrlDecoding(shortUrl);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 설문의_질문들_조회() {
        // give
        int surveyId = 1;

        // when
        List<QuestionInfo> questionList = surveyService.getQuestionInfo(surveyId);

        // then
        assertThat(questionList.size()).isEqualTo(10);
    }

    @Test
    void 설문_주인_유저는_지금까지_누적된_설문_결과의_모든_데이터를_조회할_수_있다() {
        // given
        int createUserId = 4;
        int surveyId = 1;
        User user = User.builder()
                .id(4)
                .email("email2@naver.com")
                .password("NONE")
                .nickname("김태영1")
                .picture("http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg")
                .socialType("KAKAO")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        // when
        SurveyResultDetail result = surveyService.getSurveyResult(createUserId, surveyId, user);

        // then
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getSurveyCompletionWithAnswers()).size().isGreaterThan(1);
        assertThat(result.getSelfKeywords()).size().isGreaterThan(1);
        assertThat(result.getFeedBackKeywords()).size().isGreaterThan(1);
        assertThat(result.getType()).isNotNull();

    }
    @Test
    void 설문_주인이_아닌_유저는_타인과의_답변_비교_데이터를_제외한_지금까지_누적된_설문_결과_데이터를_조회할_수_있다() {
        // given
        int createUserId = 4;
        int surveyId = 1;
        User user = User.builder()
                .id(3)
                .email("email2@naver.com")
                .password("NONE")
                .nickname("김태영1")
                .picture("http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg")
                .socialType("KAKAO")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        // when
        SurveyResultDetail result = surveyService.getSurveyResult(createUserId, surveyId, user);

        // then
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getSurveyCompletionWithAnswers()).isNull();
        assertThat(result.getSelfKeywords()).size().isGreaterThan(1);
        assertThat(result.getType()).isNotNull();

    }

    @Test
    void 설문_주인_유저는_지금까지_누적된_설문_결과의_모든_데이터를_조회할_수_있다_이때_타인_설문이_1개도_수행되지_않으면_해당_데이터는_null로_조회된다() {
        // given
        int createUserId = 2;
        int surveyId = 1;
        User user = User.builder()
                .id(2)
                .email("email0@naver.com")
                .password("NONE")
                .nickname("정수범")
                .picture("http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg")
                .socialType("KAKAO")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        // when
        SurveyResultDetail result = surveyService.getSurveyResult(createUserId, surveyId, user);

        // then
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getSurveyCompletionWithAnswers()).size().isGreaterThan(1);
        assertThat(result.getSelfKeywords()).size().isGreaterThan(1);
        assertThat(result.getFeedBackKeywords()).isNull();
        assertThat(result.getType()).isNotNull();

    }



}