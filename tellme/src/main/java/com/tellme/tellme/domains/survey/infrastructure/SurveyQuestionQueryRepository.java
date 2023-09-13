package com.tellme.tellme.domains.survey.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domains.survey.entity.Question;
import com.tellme.tellme.domains.survey.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.tellme.tellme.domains.survey.entity.QQuestion.question1;
import static com.tellme.tellme.domains.survey.entity.QSurveyQuestion.surveyQuestion;

@Repository
@RequiredArgsConstructor
public class SurveyQuestionQueryRepository {
    private final JPAQueryFactory query;

    public List<Question> getQuestionList(Survey survey){
        return query
                .select(
                        question1
                )
                .from(surveyQuestion)
                .join(surveyQuestion.question, question1)
                .where(surveyQuestion.survey.eq(survey))
                .orderBy(
                        question1.orderNumber.asc()
                )
                .fetch();
    }
}
