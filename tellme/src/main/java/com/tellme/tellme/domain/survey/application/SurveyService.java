package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.domain.survey.entity.*;
import com.tellme.tellme.domain.survey.persistence.*;
import com.tellme.tellme.domain.survey.presentation.SurveyDto;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyCompletionRepository surveyCompletionRepository;
    private final SurveyCompletionQueryRepository surveyCompletionQueryRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final SurveyQuestionQueryRepository surveyQuestionQueryRepository;
    private final UserRepository userRepository;
    private final SurveyShortUrlRepository surveyShortUrlRepository;

    public SurveyCompletion saveAnswer(String shortUrl, SurveyDto.Answer answer, Authentication authentication) {

        SurveyShortUrl surveyShortUrl = surveyShortUrlRepository.findByUrl(shortUrl);

        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            answer.setUuid(String.valueOf(userDetails.getId()));
        }
        answer.setSurvey(surveyRepository.findById(surveyShortUrl.getSurveyId()).get());

        SurveyCompletion surveyCompletion = surveyCompletionRepository.save(answer.toSurveyCompletion());
        for (SurveyDto.AnswerContent answerContent : answer.getAnswerContentList()) {
            Question question = questionRepository.findById(answerContent.getQuestion()).get();
            surveyAnswerRepository.save(answerContent.toSurveyAnswer(surveyCompletion, question));
        }
        return surveyCompletion;
    }

    public List<SurveyAnswer> getSurveyResult(long userId, int surveyId) {
        User user = userRepository.findById(userId).get();
        SurveyCompletion surveyCompletion = surveyCompletionQueryRepository.findByUserIdAndSurveyId(user, surveyId);
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findBySurveyCompletion(surveyCompletion);
        // TODO 선택한 답변에따라서 키워드 출력
        return surveyAnswerList;
    }

    public List<SurveyDto.SurveyCompletionWithAnswers> getSurveyResultDetail(long userId, int surveyId) {
        Survey survey = surveyRepository.findById(surveyId).get();
        User user = userRepository.findById(userId).get();

        List<Question> questionList = surveyQuestionQueryRepository.getQuestionList(survey);
        List<SurveyDto.SurveyCompletionWithAnswers> surveyCompletionWithAnswersList = new ArrayList<>();

        for (Question question : questionList) {
            Character answerToMe = surveyCompletionQueryRepository.getAnswerToMe(user, question);
            SurveyDto.SurveyCompletionWithAnswers answer = new SurveyDto.SurveyCompletionWithAnswers();
            answer.setQuestion(question.getQuestion());
            answer.setAnswerToMe(answerToMe);

            //  Todo 최빈값

            surveyCompletionWithAnswersList.add(answer);
        }
        return surveyCompletionWithAnswersList;

    }

    public String share(int surveyId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        SurveyShortUrl findShortUrl = surveyShortUrlRepository.findBySurveyIdAndUserId(surveyId, user.getId());
        if(findShortUrl != null) {
            return findShortUrl.getUrl();
        }

        long maxCount = surveyShortUrlRepository.count() + 1;
        String url = Base64.getUrlEncoder().encodeToString(String.valueOf(maxCount).getBytes());
        SurveyShortUrl shortUrl = SurveyShortUrl.builder()
                .surveyId(surveyId)
                .userId(user.getId())
                .url(url)
                .build();
        surveyShortUrlRepository.save(shortUrl);

        return shortUrl.getUrl();
    }
}
