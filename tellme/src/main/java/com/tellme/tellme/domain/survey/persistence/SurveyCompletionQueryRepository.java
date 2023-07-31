package com.tellme.tellme.domain.survey.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domain.survey.entity.QSurveyCompletion;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.tellme.tellme.domain.survey.entity.QSurveyCompletion.surveyCompletion;

@Repository
@RequiredArgsConstructor
public class SurveyCompletionQueryRepository {
    private final JPAQueryFactory query;

    public SurveyCompletion findByUserIdAndSurveyId(int userId, int surveyId) {
        return query
                .select(
                        surveyCompletion
                )
                .from(surveyCompletion)
                .where(surveyCompletion.user.id.eq(userId),
                        surveyCompletion.survey.id.eq(surveyId)
                )
                .fetchOne();
    }
}
