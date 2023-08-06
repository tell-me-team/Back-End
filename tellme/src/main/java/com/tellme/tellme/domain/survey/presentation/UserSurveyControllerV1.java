package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserSurveyControllerV1 {

    private final SurveyService surveyService;

    @GetMapping("survey-results/{userId}/{surveyId}")
    @Operation(summary = "설문 결과")
    public BaseResponse<SurveyDto.SurveyResultDetail> getSurveyResult(@PathVariable int userId, @PathVariable int surveyId, Authentication authentication){
        return BaseResponse.ok(surveyService.getSurveyResult(userId, surveyId, authentication));
    }

    @GetMapping("survey-results/{userId}/{surveyId}/details")
    @Operation(summary = "설문 결과 상세보기")
    public BaseResponse<List<SurveyDto.SurveyCompletionWithAnswers>> getSurveyResultDetail(@PathVariable int userId, @PathVariable int surveyId){
        return BaseResponse.ok(surveyService.getSurveyResultDetail(userId, surveyId));
    }
}
