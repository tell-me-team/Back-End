package com.tellme.tellme.domain.survey.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.tellme.tellme.domain.survey.entity.QQuestion.question1;
import static com.tellme.tellme.domain.survey.entity.QSurveyQuestion.surveyQuestion;

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
