package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.domain.survey.entity.*;
import com.tellme.tellme.domain.survey.persistence.*;
import com.tellme.tellme.domain.survey.presentation.SurveyDto;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public SurveyCompletion saveAnswer(int surveyId, String userId, SurveyDto.Answer answer) {
        answer.setSurvey(surveyRepository.findById(surveyId).get());

        SurveyCompletion surveyCompletion = surveyCompletionRepository.save(answer.toSurveyCompletion());
        for (SurveyDto.AnswerContent answerContent : answer.getAnswerContentList()) {
            Question question = questionRepository.findById(answerContent.getQuestion()).get();
            surveyAnswerRepository.save(answerContent.toSurveyAnswer(surveyCompletion,question));
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

        for(Question question : questionList){
            Character answerToMe = surveyCompletionQueryRepository.getAnswerToMe(user, question);
            SurveyDto.SurveyCompletionWithAnswers answer = new SurveyDto.SurveyCompletionWithAnswers();
            answer.setQuestion(question.getQuestion());
            answer.setAnswerToMe(answerToMe);

            //  Todo 최빈값

            surveyCompletionWithAnswersList.add(answer);
        }
        return surveyCompletionWithAnswersList;

    }
}
