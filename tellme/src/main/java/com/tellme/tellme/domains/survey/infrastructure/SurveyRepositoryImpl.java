package com.tellme.tellme.domains.survey.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tellme.tellme.domains.survey.entity.*;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.survey.application.port.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tellme.tellme.domains.survey.entity.QQuestion.question1;
import static com.tellme.tellme.domains.survey.entity.QSurveyQuestion.surveyQuestion;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepository{

    private final SurveyCompletionJpaRepository surveyCompletionJpaRepository;
    private final SurveyCompletionQueryRepository surveyCompletionQueryRepository;
    private final SurveyAnswerJpaRepository surveyAnswerJpaRepository;
    private final SurveyJpaRepository surveyJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final SurveyShortUrlJpaRepository surveyShortUrlJpaRepository;
    private final SurveyResultJpaRepository surveyResultJpaRepository;

    private final JPAQueryFactory query;

    @Override
    public SurveyCompletion saveSurveyCompletion(SurveyCompletion surveyCompletion) {
        return surveyCompletionJpaRepository.save(surveyCompletion);
    }

    @Override
    public SurveyAnswer saveSurveyAnswer(SurveyAnswer surveyAnswer) {
        return surveyAnswerJpaRepository.save(surveyAnswer);
    }

    @Override
    public SurveyShortUrl saveSurveyShortUrl(SurveyShortUrl surveyShortUrl) {
        return surveyShortUrlJpaRepository.save(surveyShortUrl);
    }

    @Override
    public Optional<Question> findQuestionById(int questionId) {
        return questionJpaRepository.findById(questionId);
    }

    @Override
    public Optional<Survey> findSurveyById(int surveyId) {
        return surveyJpaRepository.findById(surveyId);
    }

    @Override
    public long surveyShortUrlCount() {
        return surveyShortUrlJpaRepository.count();
    }

    @Override
    public SurveyCompletion findByUniqueIdAndSurveyAndUserEntity(String uniqueId, Survey survey, UserEntity userEntity) {
        return surveyCompletionJpaRepository.findByUniqueIdAndSurveyAndUserEntity(uniqueId, survey, userEntity);
    }

    @Override
    public List<SurveyCompletion> findByUserEntity(UserEntity userEntity) {
        return surveyCompletionJpaRepository.findByUserEntity(userEntity);
    }

    @Override
    public SurveyCompletion findByUserEntityAndUniqueIdAndSurvey(UserEntity createUserEntity, String userId, Survey survey) {
        return surveyCompletionJpaRepository.findByUserEntityAndUniqueIdAndSurvey(createUserEntity, userId, survey);
    }

    @Override
    public List<SurveyCompletion> findByUserEntityAndUniqueIdNotAndSurvey(UserEntity createUserEntity, String uniqueId, Survey survey) {
        return surveyCompletionJpaRepository.findByUserEntityAndUniqueIdNotAndSurvey(createUserEntity, uniqueId, survey);
    }

    @Override
    public Optional<SurveyCompletion> findByUserEntityAndSurveyAndUniqueId(UserEntity userEntity, Survey survey, String uniqueId) {
        return surveyCompletionJpaRepository.findByUserEntityAndSurveyAndUniqueId(userEntity, survey, uniqueId);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionJpaRepository.findByUniqueId(uniqueId);
    }

    @Override
    public List<SurveyAnswer> findBySurveyCompletion(SurveyCompletion surveyCompletion) {
        return surveyAnswerJpaRepository.findBySurveyCompletion(surveyCompletion);
    }

    @Override
    public Question findByQuestion(String question) {
        return questionJpaRepository.findByQuestion(question);
    }

    @Override
    public List<Question> getQuestionList(Survey survey) {
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

    @Override
    public Optional<SurveyShortUrl> findByUrl(String url) {
        return surveyShortUrlJpaRepository.findByUrl(url);
    }

    @Override
    public Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId) {
        return surveyShortUrlJpaRepository.findBySurveyIdAndUserId(surveyId, userId);
    }

    @Override
    public SurveyResult findByTypeNumber(int typeNumber) {
        return surveyResultJpaRepository.findByTypeNumber(typeNumber);
    }
}
