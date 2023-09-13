package com.tellme.tellme.domains.survey.infrastructure;

import com.tellme.tellme.domains.survey.entity.Survey;
import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/survey-repository-test.sql")
class SurveyCompletionJpaRepositoryTest {

    @Autowired
    private SurveyCompletionJpaRepository surveyCompletionJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private SurveyJpaRepository surveyJpaRepository;

    @Test
    void 설문_참여한_유저인지_확인() {
        //given
        String uniqueId = "3";
        int userId = 3;
        int surveyId = 1;

        Survey survey = surveyJpaRepository.findById(surveyId).orElseThrow( () -> new RuntimeException("존재하지 않는 설문입니다."));
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow( () -> new RuntimeException("존재하지 않는 유저 입니다."));

        // when
        SurveyCompletion surveyCompletion = surveyCompletionJpaRepository.findByUniqueIdAndSurveyAndUserEntity(uniqueId, survey, userEntity);

        //then
        assertThat(surveyCompletion.getUniqueId()).isEqualTo("3");
    }

    @Test
    void 설문_참여하지_않은_유저이면_null_반환() {
        //given
        String uniqueId = "31";
        int userId = 3;
        int surveyId = 1;

        Survey survey = surveyJpaRepository.findById(surveyId).orElseThrow( () -> new RuntimeException("존재하지 않는 설문입니다."));
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow( () -> new RuntimeException("존재하지 않는 유저 입니다."));

        // when
        SurveyCompletion surveyCompletion = surveyCompletionJpaRepository.findByUniqueIdAndSurveyAndUserEntity(uniqueId, survey, userEntity);

        //then
        assertThat(surveyCompletion).isNull();
    }

    @Test
    void 설문에_참여한_유저_확인(){
        // given
        int userId = 3;

        // when
        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );
        List<SurveyCompletion> result = surveyCompletionJpaRepository.findByUserEntity(userEntity);

        // then
        assertThat(result).hasSizeGreaterThan(0);
    }

    @Test
    void 내자신에_대한_설문_데이터_조회(){
        // given
        int createUserId = 3;
        String userId = "3";
        int surveyId = 1;

        // when
        UserEntity createUserEntity = userJpaRepository.findById(createUserId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );
        Survey survey = surveyJpaRepository.findById(surveyId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 설문입니다.")
        );

        SurveyCompletion result = surveyCompletionJpaRepository.findByUserEntityAndUniqueIdAndSurvey(createUserEntity, userId, survey);

        // then
        assertThat(result.getUniqueId()).isEqualTo("3");
    }

    @Test
    void 나의설문에_나를_제외한_사람들에_설문_답변(){
        // given
        int createUserId = 3;
        String userId = "3";
        int surveyId = 1;

        // when
        UserEntity createUserEntity = userJpaRepository.findById(createUserId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );
        Survey survey = surveyJpaRepository.findById(surveyId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 설문입니다.")
        );

        List<SurveyCompletion> result = surveyCompletionJpaRepository.findByUserEntityAndUniqueIdNotAndSurvey(createUserEntity, userId, survey);

        // then
        assertThat(result).hasSizeGreaterThan(0);
    }

    @Test
    void 설문_생성여부_확인(){
        // given
        int userId = 3;
        String uniqueId = "3";
        int surveyId = 1;

        // when
        UserEntity createUserEntity = userJpaRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );
        Survey survey = surveyJpaRepository.findById(surveyId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 설문입니다.")
        );

        SurveyCompletion result = surveyCompletionJpaRepository.findByUserEntityAndSurveyAndUniqueId(createUserEntity, survey, uniqueId).orElseThrow(
                () -> new RuntimeException("설문을 생성하지 않았습니다.")
        );

        // then
        assertThat(result.getSurvey()).isNotNull();
    }

    @Test
    void 존재하는_설문_조사_완료_한_사람들_조회(){
        // given
        String uniqueId = "3";

        // when
        List<SurveyCompletion> result = surveyCompletionJpaRepository.findByUniqueId(uniqueId);

        // then
        assertThat(result).hasSizeGreaterThan(0);
    }
}