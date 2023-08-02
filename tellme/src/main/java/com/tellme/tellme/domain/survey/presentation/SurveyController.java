package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/{surveyId}/{userId}")
    @ResponseBody
    private BaseResponse<SurveyCompletion> saveAnswer(@PathVariable("surveyId") int surveyId,
                                                      @PathVariable("userId") String userId,
                                                      @RequestBody SurveyDto.Answer answer){
        return BaseResponse.ok(surveyService.saveAnswer(surveyId, userId, answer));
    }
}
