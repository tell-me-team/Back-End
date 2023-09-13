package com.tellme.tellme.domains.survey.application;

import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.exception.ErrorStatus;
import com.tellme.tellme.domains.survey.application.port.SurveyRepository;
import com.tellme.tellme.domains.survey.entity.*;
import com.tellme.tellme.domains.user.application.port.UserRepository;
import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.tellme.tellme.domains.survey.presentation.SurveyDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    @Transactional
    public SurveyResultInfo saveAnswer(int surveyId, int userId, Answer answer, UserEntity authenticationUserEntity, String uniqueId) {
        String shortUrl = null;
        Survey survey = surveyRepository.findSurveyById(surveyId).get();
        User createUser = userRepository.findById(userId).get();

        if (!isSurveyQuestionCount(survey, answer)) {
            throw new BaseException(ErrorStatus.SURVEY_ANSWER_INSUFFICIENT);
        }

        if (authenticationUserEntity != null ) {
            uniqueId = String.valueOf(authenticationUserEntity.getId());
            if(authenticationUserEntity.getId() == userId){
                shortUrl = getShareUrl(survey, authenticationUserEntity);
            }
        }

        SurveyCompletion alreadyExistSurveyCompletion = surveyRepository.findByUniqueIdAndSurveyAndUserEntity(uniqueId, survey, UserEntity.from(createUser));

        if (alreadyExistSurveyCompletion == null) {
            saveSurveyAnswer(survey, createUser, answer, uniqueId);
        }else {
            if(!isCreateUserSurveyCompletion(createUser, alreadyExistSurveyCompletion)) {
                throw new BaseException(ErrorStatus.SURVEY_ALREADY_COMPLETED);
            }
            updateSurveyAnswers(answer, alreadyExistSurveyCompletion);
        }

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
    public SurveyResultDetail getSurveyResult(int createUserId, int surveyId, UserEntity authenticationUserEntity) {

        User createUser = userRepository.findById(createUserId).get();
        Survey survey = surveyRepository.findSurveyById(surveyId).get();

        if(isSurveyCompletionFindCreateUser(UserEntity.from(createUser), survey, createUser.getId())){
            throw new BaseException(ErrorStatus.SURVEY_NOT_CREATE);
        }

        SurveyCompletion surveyCompletionToMe = surveyRepository.findByUserEntityAndUniqueIdAndSurvey(UserEntity.from(createUser), String.valueOf(createUserId), survey);
        List<AnswerContent> answerContentList = new ArrayList<>();
        List<SurveyCompletionWithAnswers> surveyCompletionWithAnswersList = new ArrayList<>();
        for (SurveyAnswer surveyAnswer : surveyCompletionToMe.getSurveyAnswers()) {
            AnswerContent answerContent = AnswerContent.builder()
                    .question(surveyAnswer.getQuestion().getId())
                    .answer(surveyAnswer.getAnswer())
                    .build();
            answerContentList.add(answerContent);

            SurveyCompletionWithAnswers surveyCompletionWithAnswers = SurveyCompletionWithAnswers.builder()
                    .question(surveyAnswer.getQuestion().getQuestion())
                    .answerToMe(surveyAnswer.getAnswer() == 'A' ? surveyAnswer.getQuestion().getAnswerA() : surveyAnswer.getQuestion().getAnswerB())
                            .build();
            surveyCompletionWithAnswersList.add(surveyCompletionWithAnswers);
        }

        SurveyResult surveyResultFromMe = generateCombinedAnswerResult(answerContentList, survey);


        List<SurveyCompletion> surveyCompletionOtherToMe = surveyRepository.findByUserEntityAndUniqueIdNotAndSurvey(UserEntity.from(createUser), String.valueOf(createUserId), survey);

        if (surveyCompletionOtherToMe.size() == 0) {

            if(authenticationUserEntity == null){
                return SurveyResultDetail.builder()
                        .nickname(createUser.getNickname())
                        .type(surveyResultFromMe.getType())
                        .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build();
            }

            if(authenticationUserEntity.getId() != createUserId){
                return SurveyResultDetail.builder()
                        .nickname(createUser.getNickname())
                        .type(surveyResultFromMe.getType())
                        .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build();
            }


            return SurveyResultDetail.builder()
                    .nickname(createUser.getNickname())
                    .type(surveyResultFromMe.getType())
                    .surveyCompletionWithAnswers(surveyCompletionWithAnswersList)
                    .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                            surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                    .title(surveyResultKeyword.getTitle())
                                    .build()
                    ).collect(Collectors.toList()))
                    .build();



        }else {
            List<AnswerContent> answerContentFromOtherToMeList = new ArrayList<>();


            Map<Integer, Map<Character, Integer>> questionAnswerCountMap = countQuestionAnswer(surveyCompletionOtherToMe);
            Map<Integer, Character> mostSelectedAnswers = findMostSelectedAnswers(questionAnswerCountMap);

            int surveyCompletionWithAnswersIndex = 1;
            for(SurveyCompletionWithAnswers surveyCompletionWithAnswers : surveyCompletionWithAnswersList){
                Character answer = mostSelectedAnswers.get(surveyCompletionWithAnswersIndex);

                Question question = surveyRepository.findByQuestion(surveyCompletionWithAnswers.getQuestion());

                surveyCompletionWithAnswers.setAnswerToOther(answer == 'A' ? question.getAnswerA() : question.getAnswerB());
                surveyCompletionWithAnswersIndex++;
            }

            for (SurveyCompletion surveyCompletion : surveyCompletionOtherToMe) {
                for (SurveyAnswer surveyAnswer : surveyCompletion.getSurveyAnswers()) {
                    AnswerContent answerContent = AnswerContent.builder()
                            .question(surveyAnswer.getQuestion().getId())
                            .answer(surveyAnswer.getAnswer())
                            .build();
                    answerContentFromOtherToMeList.add(answerContent);

                }


            }
            SurveyResult surveyResultFromOther = generateCombinedAnswerResult(answerContentFromOtherToMeList, survey);

            if(authenticationUserEntity == null){
                return SurveyResultDetail.builder()
                        .nickname(createUser.getNickname())
                        .type(surveyResultFromMe.getType())
                        .feedBackKeywords(surveyResultFromOther.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build();
            }


            if(authenticationUserEntity.getId() != createUserId){
                return SurveyResultDetail.builder()
                        .nickname(createUser.getNickname())
                        .type(surveyResultFromMe.getType())
                        .feedBackKeywords(surveyResultFromOther.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                                surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                        .title(surveyResultKeyword.getTitle())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build();
            }


            return SurveyResultDetail.builder()
                    .nickname(createUser.getNickname())
                    .type(surveyResultFromMe.getType())
                    .surveyCompletionWithAnswers(surveyCompletionWithAnswersList)
                    .feedBackKeywords(surveyResultFromOther.getSurveyResultKeywords().stream().map(
                            surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                    .title(surveyResultKeyword.getTitle())
                                    .build()
                    ).collect(Collectors.toList()))
                    .selfKeywords(surveyResultFromMe.getSurveyResultKeywords().stream().map(
                            surveyResultKeyword -> SurveyResultKeywordInfo.builder()
                                    .title(surveyResultKeyword.getTitle())
                                    .build()
                    ).collect(Collectors.toList()))
                    .build();
        }

    }

    @Transactional(readOnly = true)
    public SurveyInfo shortUrlDecoding(String shortUrl) {
        SurveyShortUrl surveyShortUrl = surveyRepository.findByUrl(shortUrl).orElseThrow(
                ()-> new RuntimeException("shortUrl이 존재하지 않습니다.")
        );
        int surveyId = surveyShortUrl.getSurveyId();
        int userId = surveyShortUrl.getUserId();

        User user = userRepository.findById(userId).get();
        int userCount = surveyRepository.findByUserEntity(UserEntity.from(user)).size();

        return SurveyInfo.builder()
                .surveyId(surveyId)
                .userId(userId)
                .userCount(userCount)
                .nickname(user.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuestionInfo> getQuestionInfo(int surveyId) {
        Survey survey = surveyRepository.findSurveyById(surveyId).get();
        List<Question> questionList = surveyRepository.getQuestionList(survey);

        return questionList.stream()
                .map(question -> QuestionInfo.builder()
                        .question(question.getQuestion())
                        .answerA(question.getAnswerA())
                        .answerB(question.getAnswerB())
                        .build()).collect(Collectors.toList());
    }

    private static void updateSurveyAnswers(Answer answer, SurveyCompletion alreadyExistSurveyCompletion) {
        List<AnswerContent> answerContentList = answer.getAnswerContentList();

        for (int i = 0; i < answerContentList.size(); i++) {
            char newAnswer = answerContentList.get(i).getAnswer();
            SurveyAnswer alreadyExistSurveyAnswer = alreadyExistSurveyCompletion.getSurveyAnswers().get(i);
            alreadyExistSurveyAnswer.updateSurveyAnswer(newAnswer);
        }
    }

    private static boolean isCreateUserSurveyCompletion(User createUser, SurveyCompletion alreadyExistSurveyCompletion) {
        return alreadyExistSurveyCompletion.getUniqueId().equals(Integer.toString(createUser.getId()));
    }

    private boolean isSurveyQuestionCount(Survey survey, Answer answer) {
        long questionCount = surveyRepository.getQuestionList(survey).stream().count();
        return questionCount == answer.getAnswerContentList().size();
    }

    private void saveSurveyAnswer(Survey survey, User createUser, Answer answer, String uniqueId) {

        SurveyCompletion surveyCompletion = surveyRepository.saveSurveyCompletion(answer.toSurveyCompletion(survey, UserEntity.from(createUser), uniqueId));
        for (AnswerContent answerContent : answer.getAnswerContentList()) {
            Question question = surveyRepository.findQuestionById(answerContent.getQuestion()).get();
            surveyRepository.saveSurveyAnswer(answerContent.toSurveyAnswer(surveyCompletion, question));
        }
    }

    private String getShareUrl(Survey survey, UserEntity userEntity) {
        SurveyShortUrl findShortUrl = surveyRepository.findBySurveyIdAndUserId(survey.getId(), userEntity.getId()).orElse(null);
        if (findShortUrl != null) {
            return findShortUrl.getUrl();
        }

        long maxCount = surveyRepository.surveyShortUrlCount() + 1;
        String url = Base64.getUrlEncoder().encodeToString(String.valueOf(maxCount).getBytes());
        SurveyShortUrl shortUrl = SurveyShortUrl.builder()
                .surveyId(survey.getId())
                .userId(userEntity.getId())
                .url(url)
                .build();
        surveyRepository.saveSurveyShortUrl(shortUrl);

        return shortUrl.getUrl();
    }


    private boolean isSurveyCompletionFindCreateUser(UserEntity createUserEntity, Survey survey, int uniqueId){
        return surveyRepository.findByUserEntityAndSurveyAndUniqueId(createUserEntity, survey, String.valueOf(uniqueId)).isEmpty();
    }

    private SurveyResult generateCombinedAnswerResult(List<AnswerContent> answerContentList, Survey survey) {
        List<Question> questionList = surveyRepository.getQuestionList(survey);

        StringBuilder answerResult = new StringBuilder();
        int index = 0;
        for (AnswerContent answerContent : answerContentList) {

            if (answerContent.getAnswer() == 'A') {
                answerResult.append(questionList.get(index).getAnswerAResult());
            } else {
                answerResult.append(questionList.get(index).getAnswerBResult());
            }
            index++;
            if (index == questionList.size()) index = 0;
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
        return surveyRepository.findByTypeNumber(mostCommonDigit);
    }

    private Map<Integer, Map<Character, Integer>> countQuestionAnswer(List<SurveyCompletion> surveyCompletions) {
        Map<Integer, Map<Character, Integer>> questionAnswerCountMap = new HashMap<>();

        for (SurveyCompletion surveyCompletion : surveyCompletions) {
            for (SurveyAnswer surveyAnswer : surveyCompletion.getSurveyAnswers()) {
                int questionId = surveyAnswer.getQuestion().getId();
                char answer = surveyAnswer.getAnswer();

                questionAnswerCountMap.computeIfAbsent(questionId, k -> new HashMap<>())
                        .put(answer, questionAnswerCountMap.getOrDefault(questionId, new HashMap<>()).getOrDefault(answer, 0) + 1);
            }
        }

        return questionAnswerCountMap;
    }

    private Map<Integer, Character> findMostSelectedAnswers(Map<Integer, Map<Character, Integer>> questionAnswerCountMap) {
        Map<Integer, Character> mostSelectedAnswers = new HashMap<>();

        for (Map.Entry<Integer, Map<Character, Integer>> entry : questionAnswerCountMap.entrySet()) {
            int questionId = entry.getKey();
            Map<Character, Integer> answerCountMap = entry.getValue();

            char mostSelectedAnswer = findMostSelectedAnswer(answerCountMap);
            mostSelectedAnswers.put(questionId, mostSelectedAnswer);
        }

        return mostSelectedAnswers;
    }

    private char findMostSelectedAnswer(Map<Character, Integer> answerCountMap) {
        char mostSelectedAnswer = '\0';
        int maxCount = 0;

        for (Map.Entry<Character, Integer> entry : answerCountMap.entrySet()) {
            char answer = entry.getKey();
            int count = entry.getValue();

            if (count > maxCount) {
                mostSelectedAnswer = answer;
                maxCount = count;
            }
        }

        return mostSelectedAnswer;
    }
}
