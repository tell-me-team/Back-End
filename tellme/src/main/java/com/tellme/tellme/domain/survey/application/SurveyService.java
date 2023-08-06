package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.exception.ErrorStatus;
import com.tellme.tellme.domain.survey.entity.*;
import com.tellme.tellme.domain.survey.persistence.*;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.tellme.tellme.domain.survey.presentation.SurveyDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final SurveyCompletionRepository surveyCompletionRepository;
    private final SurveyCompletionQueryRepository surveyCompletionQueryRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final SurveyQuestionQueryRepository surveyQuestionQueryRepository;
    private final UserRepository userRepository;
    private final SurveyShortUrlRepository surveyShortUrlRepository;
    private final SurveyResultRepository surveyResultRepository;

    @Transactional(readOnly = true)
    public SurveyResultInfo saveAnswer(int surveyId, int userId, Answer answer, Authentication authentication, HttpServletRequest httpServletRequest) {
        String shortUrl = null;
        String uniqueId = httpServletRequest.getSession().getId();
        Survey survey = surveyRepository.findById(surveyId).get();

        // 질문갯수 확인
        if (!isSurveyQuestionCount(survey, answer)) {
            throw new BaseException(ErrorStatus.SURVEY_ANSWER_INSUFFICIENT);
        }

        if (authentication != null) {
            User userDetails = (User) authentication.getPrincipal();
            uniqueId = String.valueOf(userDetails.getId());
            shortUrl = getShareUrl(survey, userDetails);
        }

        if (isSurveyAlreadyCompleted(uniqueId)) {
            throw new BaseException(ErrorStatus.SURVEY_ALREADY_COMPLETED);
        }

        User createUser = userRepository.findById(userId).get();
        saveSurveyAnswer(survey, createUser, answer, uniqueId);
        SurveyResult surveyResult = generateCombinedAnswerResult(answer.getAnswerContentList(), survey);

        return SurveyResultInfo.builder()
                .type(surveyResult.getType())
                .content(surveyResult.getContent())
                .typeNumber(surveyResult.getTypeNumber())
                .keywordInfo(surveyResult.getSurveyResultKeywords().stream()
                        .map(keyword -> SurveyResultKeywordInfo.builder()
                                .title(keyword.getTitle())
                                .build())
                        .collect(Collectors.toList()))
                .shortUrl(shortUrl)
                .build();
    }

    @Transactional(readOnly = true)
    public SurveyResultDetail getSurveyResult(int createUserId, int surveyId, Authentication authentication) {

        // 내가 선택하는 결과값들
        User user = (User) authentication.getPrincipal();
        User createUser = userRepository.findById(createUserId).get();
        Survey survey = surveyRepository.findById(surveyId).get();

        SurveyCompletion surveyCompletionToMe = surveyCompletionRepository.findByUserAndUniqueIdAndSurvey(createUser, String.valueOf(user.getId()), survey);

        List<AnswerContent> answerContentList = new ArrayList<>();

        for (SurveyAnswer surveyAnswer : surveyCompletionToMe.getSurveyAnswers()) {
            AnswerContent answerContent = AnswerContent.builder()
                    .question(surveyAnswer.getQuestion().getId())
                    .answer(surveyAnswer.getAnswer())
                    .build();
            answerContentList.add(answerContent);
        }

        SurveyResult surveyAnswerToMe = generateCombinedAnswerResult(answerContentList, survey);

        // 다른 사람들이 선택해준 결과값들
        List<AnswerContent> answerContentToOtherToMeList = new ArrayList<>();
        StringBuilder surveyResultTypeNumbers = new StringBuilder();
        List<SurveyCompletion> surveyCompletionOtherToMe = surveyCompletionRepository.findByUserAndUniqueIdNotAndSurvey(createUser, String.valueOf(user.getId()), survey);
        for (SurveyCompletion surveyCompletion : surveyCompletionOtherToMe) {
            for (SurveyAnswer surveyAnswer : surveyCompletion.getSurveyAnswers()) {
                AnswerContent answerContent = AnswerContent.builder()
                        .question(surveyAnswer.getQuestion().getId())
                        .answer(surveyAnswer.getAnswer())
                        .build();
                answerContentToOtherToMeList.add(answerContent);
            }
            surveyResultTypeNumbers.append(generateCombinedAnswerResult(answerContentList, survey).getTypeNumber());
        }
        SurveyResult surveyAnswerToOther = calculateMode(surveyResultTypeNumbers.toString());


        return SurveyResultDetail.builder()
                .feedBackKeywords(surveyAnswerToMe.getSurveyResultKeywords().stream().map(
                        surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                .title(surveyResultKeyword.getTitle())
                                .build()
                ).collect(Collectors.toList()))
                .selfKeywords(surveyAnswerToOther.getSurveyResultKeywords().stream().map(
                        surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                .title(surveyResultKeyword.getTitle())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<SurveyCompletionWithAnswers> getSurveyResultDetail(int userId, int surveyId) {
//        Survey survey = surveyRepository.findById(surveyId).get();
//        User user = userRepository.findById(userId).get();
//
//        List<Question> questionList = surveyQuestionQueryRepository.getQuestionList(survey);
//        List<SurveyCompletionWithAnswers> surveyCompletionWithAnswersList = new ArrayList<>();
//
//        for (Question question : questionList) {
//            Character answerToMe = surveyCompletionQueryRepository.getAnswerToMe(user, question);
//
//            SurveyCompletionWithAnswers answer = SurveyCompletionWithAnswers.builder()
//                    .question(question.getQuestion())
//                    .answerToMe(answerToMe)
//                    .answerToOther("")
//                    .build(); //  TODO 최빈값
//
//            surveyCompletionWithAnswersList.add(answer);
//        }
//        return surveyCompletionWithAnswersList;
        return null;

    }

    @Transactional(readOnly = true)
    public SurveyInfo shortUrlDecoding(String shortUrl) {
        SurveyShortUrl surveyShortUrl = surveyShortUrlRepository.findByUrl(shortUrl);
        int surveyId = surveyShortUrl.getSurveyId();
        int userId = surveyShortUrl.getUserId();

        User user = userRepository.findById(userId).get();
        int userCount = surveyCompletionRepository.findByUser(user).size();

        return SurveyInfo.builder()
                .surveyId(surveyId)
                .userId(userId)
                .userCount(userCount)
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuestionInfo> getQuestionInfo(int surveyId) {
        Survey survey = surveyRepository.findById(surveyId).get();
        List<Question> questionList = surveyQuestionQueryRepository.getQuestionList(survey);

        return questionList.stream()
                .map(question -> QuestionInfo.builder()
                        .question(question.getQuestion())
                        .answerA(question.getAnswerA())
                        .answerB(question.getAnswerB())
                        .build()).collect(Collectors.toList());
    }

    private boolean isSurveyQuestionCount(Survey survey, Answer answer) {
        long questionCount = surveyQuestionQueryRepository.getQuestionList(survey).stream().count();
        System.out.println(questionCount);
        System.out.println(answer.getAnswerContentList().size());
        return questionCount == answer.getAnswerContentList().size();
    }

    private void saveSurveyAnswer(Survey survey, User user, Answer answer, String uniqueId) {
        SurveyCompletion surveyCompletion = surveyCompletionRepository.save(answer.toSurveyCompletion(survey, user, uniqueId));
        for (AnswerContent answerContent : answer.getAnswerContentList()) {
            Question question = questionRepository.findById(answerContent.getQuestion()).get();
            surveyAnswerRepository.save(answerContent.toSurveyAnswer(surveyCompletion, question));
        }
    }

    private String getShareUrl(Survey survey, User user) {
        SurveyShortUrl findShortUrl = surveyShortUrlRepository.findBySurveyIdAndUserId(survey.getId(), user.getId());
        if (findShortUrl != null) {
            return findShortUrl.getUrl();
        }

        long maxCount = surveyShortUrlRepository.count() + 1;
        String url = Base64.getUrlEncoder().encodeToString(String.valueOf(maxCount).getBytes());
        SurveyShortUrl shortUrl = SurveyShortUrl.builder()
                .surveyId(survey.getId())
                .userId(user.getId())
                .url(url)
                .build();
        surveyShortUrlRepository.save(shortUrl);

        return shortUrl.getUrl();
    }

    private boolean isSurveyAlreadyCompleted(String uniqueId) {
        return surveyCompletionRepository.findByUniqueId(uniqueId) != null;
    }

    private SurveyResult generateCombinedAnswerResult(List<AnswerContent> answerContentList, Survey survey) {
        List<Question> questionList = surveyQuestionQueryRepository.getQuestionList(survey);

        StringBuilder answerResult = new StringBuilder();
        int index = 0;
        for (AnswerContent answerContent : answerContentList) {
            if (answerContent.getAnswer() == 'A') {
                answerResult.append(questionList.get(index).getAnswerAResult());
            } else {
                answerResult.append(questionList.get(index).getAnswerBResult());
            }
            index++;
        }
        return calculateMode(answerResult.toString());
    }

    private SurveyResult calculateMode(String answerResult) {

        Map<Integer, Integer> digitCountMap = new HashMap<>();

        for (char digitChar : answerResult.toCharArray()) {
            if (Character.isDigit(digitChar)) {
                int digit = Character.getNumericValue(digitChar);
                digitCountMap.put(digit, digitCountMap.getOrDefault(digit, 0) + 1);
            }
        }

        int mostCommonDigit = -1;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : digitCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonDigit = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return surveyResultRepository.findByTypeNumber(mostCommonDigit);
    }
}
