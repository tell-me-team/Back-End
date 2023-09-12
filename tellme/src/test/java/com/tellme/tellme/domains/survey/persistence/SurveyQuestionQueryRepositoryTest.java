package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.Question;
import com.tellme.tellme.domains.survey.entity.Survey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/survey-repository-test.sql")
@Import(TestQueryConfig.class)
class SurveyQuestionQueryRepositoryTest {

    @Autowired
    private SurveyQuestionQueryRepository surveyQuestionQueryRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    void 설문의_질문리스트_가져오기(){
        // given
        Optional<Survey> survey= surveyRepository.findById(1);

        // when
        List<Question> result = surveyQuestionQueryRepository.getQuestionList(survey.get());

        // then
        assertThat(result).size().isEqualTo(10);
    }


}