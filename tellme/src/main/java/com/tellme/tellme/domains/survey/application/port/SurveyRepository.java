package com.tellme.tellme.domains.survey.application.port;

import com.tellme.tellme.domains.survey.entity.*;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository {

    SurveyCompletion saveSurveyCompletion(SurveyCompletion surveyCompletion);

    SurveyAnswer saveSurveyAnswer(SurveyAnswer surveyAnswer);

    SurveyShortUrl saveSurveyShortUrl(SurveyShortUrl surveyShortUrl);

    Optional<Question> findQuestionById(int questionId);

    Optional<Survey> findSurveyById(int surveyId);

    long surveyShortUrlCount();

    SurveyCompletion findByUniqueIdAndSurveyAndUserEntity(String uniqueId, Survey survey, UserEntity userEntity);

    List<SurveyCompletion> findByUserEntity(UserEntity userEntity);

    SurveyCompletion findByUserEntityAndUniqueIdAndSurvey(UserEntity createUserEntity, String userId, Survey survey);

    List<SurveyCompletion> findByUserEntityAndUniqueIdNotAndSurvey(UserEntity createUserEntity, String uniqueId, Survey survey);

    Optional<SurveyCompletion> findByUserEntityAndSurveyAndUniqueId(UserEntity userEntity, Survey survey, String uniqueId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

    List<SurveyAnswer> findBySurveyCompletion(SurveyCompletion surveyCompletion);

    Question findByQuestion(String question);

    List<Question> getQuestionList(Survey survey);

    Optional<SurveyShortUrl> findByUrl(String url);

    Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId);

    SurveyResult findByTypeNumber(int typeNumber);
}
