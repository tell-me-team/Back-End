package com.tellme.tellme.domains.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domains.survey.application.SurveyService;
import com.tellme.tellme.domains.survey.presentation.SurveyDto.Answer;
import com.tellme.tellme.domains.survey.presentation.SurveyDto.SurveyResultInfo;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/survey")
@RequiredArgsConstructor
public class SurveyControllerV1 {

    private final SurveyService surveyService;

    @PostMapping("/{surveyId}/{userId}/answers")
    @Operation(summary = "설문 답변", description = "토큰 필수X | 토큰X: 익명 설문 답변, 토큰O: 유저 설문 답변")
    @ResponseBody
    public BaseResponse<SurveyResultInfo> saveAnswer(@PathVariable("surveyId") int surveyId,
                                                     @PathVariable("userId") int userId,
                                                     @RequestBody Answer answer,
                                                     Authentication authentication,
                                                     HttpServletRequest httpServletRequest) {
        String uniqueId = httpServletRequest.getSession().getId();
        UserEntity authenticationUserEntity = (UserEntity) authentication.getPrincipal();
        return BaseResponse.ok(surveyService.saveAnswer(surveyId, userId, answer, authenticationUserEntity, uniqueId));
    }

    @GetMapping("/{shortUrl}")
    @Operation(summary = "설문 링크 url 디코드", description = "토큰 필수X | short url에 담긴 userId, surveyId 조회")
    @ResponseBody
    public BaseResponse<SurveyDto.SurveyInfo> shortUrlDecoding(@PathVariable String shortUrl){
        return BaseResponse.ok(surveyService.shortUrlDecoding(shortUrl));
    }

    @GetMapping("/questions/{surveyId}")
    @Operation(summary = "설문 질문 리스트", description = "토큰 필수X | survey 질문 리스트 조회")
    @ResponseBody
    public BaseResponse<List<SurveyDto.QuestionInfo>> getQuestionInfo(@PathVariable int surveyId){
        return BaseResponse.ok(surveyService.getQuestionInfo(surveyId));
    }
}
