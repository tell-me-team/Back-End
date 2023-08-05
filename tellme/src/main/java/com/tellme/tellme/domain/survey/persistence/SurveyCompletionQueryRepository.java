package com.tellme.tellme.domain.survey.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.tellme.tellme.domain.survey.entity.QSurveyAnswer.*;
import static com.tellme.tellme.domain.survey.entity.QSurveyCompletion.surveyCompletion;

@Repository
@RequiredArgsConstructor
public class SurveyCompletionQueryRepository {
    private final JPAQueryFactory query;

    public SurveyCompletion findByUserIdAndSurveyId(User user, int surveyId) {
        return query
                .select(
                        surveyCompletion
                )
                .from(surveyCompletion)
                .where(surveyCompletion.user.eq(user),
                        surveyCompletion.survey.id.eq(surveyId)
                )
                .fetchOne();
    }

    public Character getAnswerToMe(User user, Question question) {
        String userIdAsString = String.valueOf(user.getId());
        return query
                .select(
                        surveyAnswer.answer
                )
                .from(surveyCompletion)
                .join(surveyCompletion.surveyAnswers, surveyAnswer)
                .where(
                        surveyCompletion.user.eq(user),
                        surveyCompletion.uniqueId.eq(userIdAsString),
                        surveyAnswer.question.eq(question)
                )
                .fetchOne();
    }
}
