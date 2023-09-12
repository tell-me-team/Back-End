package com.tellme.tellme.domains.survey.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domains.survey.entity.Question;
import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.tellme.tellme.domains.survey.entity.QSurveyAnswer.*;
import static com.tellme.tellme.domains.survey.entity.QSurveyCompletion.surveyCompletion;

@Repository
@RequiredArgsConstructor
public class SurveyCompletionQueryRepository {
    private final JPAQueryFactory query;

    public SurveyCompletion findByUserEntityIdAndSurveyId(UserEntity userEntity, int surveyId) {
        return query
                .select(
                        surveyCompletion
                )
                .from(surveyCompletion)
                .where(surveyCompletion.userEntity.eq(userEntity),
                        surveyCompletion.survey.id.eq(surveyId)
                )
                .fetchOne();
    }

    public Character getAnswerToMe(UserEntity userEntity, Question question) {
        String userIdAsString = String.valueOf(userEntity.getId());
        return query
                .select(
                        surveyAnswer.answer
                )
                .from(surveyCompletion)
                .join(surveyCompletion.surveyAnswers, surveyAnswer)
                .where(
                        surveyCompletion.userEntity.eq(userEntity),
                        surveyCompletion.uniqueId.eq(userIdAsString),
                        surveyAnswer.question.eq(question)
                )
                .fetchOne();
    }
}
