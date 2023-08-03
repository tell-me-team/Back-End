package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.presentation.SurveyDto.Answer;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "설문 답변")
    @ResponseBody
    public BaseResponse saveAnswer(@PathVariable("surveyId") int surveyId,
                                   @PathVariable("userId") long userId,
                                   @RequestBody Answer answer,
                                   Authentication authentication) {
        return surveyService.saveAnswer(surveyId, userId, answer, authentication);
    }

    @GetMapping("/share/{surveyId}")
    @Operation(summary = "설문 링크 공유 url 생성")
    @ResponseBody
    public BaseResponse<String> share(@PathVariable("surveyId") int surveyId,
                                       Authentication authentication){

        return BaseResponse.ok(surveyService.share(surveyId, authentication));
    }

    @GetMapping("/{shortUrl}")
    @Operation(summary = "설문 링크 url 디코드")
    @ResponseBody
    public BaseResponse<SurveyDto.SurveyInfo> shortUrlDecoding(@PathVariable String shortUrl){
        return BaseResponse.ok(surveyService.shortUrlDecoding(shortUrl));
    }

    @GetMapping("/questions/{surveyId}")
    @Operation(summary = "설문 질문 리스트")
    @ResponseBody
    public BaseResponse<List<SurveyDto.QuestionInfo>> getQuestionInfo(@PathVariable int surveyId){
        return BaseResponse.ok(surveyService.getQuestionInfo(surveyId));
    }
}
