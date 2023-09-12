package com.tellme.tellme.domain.survey.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.tellme.tellme.domain.survey.entity.QSurveyAnswer.*;
import static com.tellme.tellme.domain.survey.entity.QSurveyCompletion.surveyCompletion;

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
