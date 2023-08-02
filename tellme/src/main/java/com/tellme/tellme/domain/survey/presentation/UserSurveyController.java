package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserSurveyController {

    private final SurveyService surveyService;

    @GetMapping("survey-results/{userId}/{surveyId}")
    @Operation(summary = "설문 결과")
    public BaseResponse<List<SurveyAnswer>> getSurveyResult(@PathVariable long userId, @PathVariable int surveyId){
        return BaseResponse.ok(surveyService.getSurveyResult(userId, surveyId));
    }

    @GetMapping("survey-results/{userId}/{surveyId}/details")
    @Operation(summary = "설문 결과 상세보기")
    public BaseResponse<List<SurveyDto.SurveyCompletionWithAnswers>> getSurveyResultDetail(@PathVariable long userId, @PathVariable int surveyId){
        return BaseResponse.ok(surveyService.getSurveyResultDetail(userId, surveyId));
    }
}
