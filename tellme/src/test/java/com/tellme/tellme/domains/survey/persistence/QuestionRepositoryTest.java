package com.tellme.tellme.domains.survey.persistence;


import com.tellme.tellme.domains.survey.entity.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("/sql/survey-repository-test.sql")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 질문을_통해_Question_객체_데이터를_조회할_수_있다() {
        // given
        String question = "무더운 여름, 휴일을 보내는 모습은?";

        // when
        Question result = questionRepository.findByQuestion(question);

        // then
        assertThat(result.getQuestion()).isEqualTo(question);
    }
}
