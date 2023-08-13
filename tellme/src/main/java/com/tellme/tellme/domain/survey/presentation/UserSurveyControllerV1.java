package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserSurveyControllerV1 {

    private final SurveyService surveyService;

    @CrossOrigin("*")
    @GetMapping("survey-results/{userId}/{surveyId}")
    @Operation(summary = "설문 결과", description = "토큰 필수X | 유저가 만든 설문 결과 조회")
    public BaseResponse<SurveyDto.SurveyResultDetail> getSurveyResult(@PathVariable int userId, @PathVariable int surveyId, Authentication authentication){
        return BaseResponse.ok(surveyService.getSurveyResult(userId, surveyId, authentication));
    }
}
