package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/{surveyId}/{userId}")
    @ResponseBody
    private BaseResponse<SurveyCompletion> saveAnswer(@PathVariable("surveyId") int surveyId,
                                                      @PathVariable("userId") String uuid,
                                                      @RequestBody SurveyDto.Answer answer,
                                                      Authentication authentication) {
        return BaseResponse.ok(surveyService.saveAnswer(surveyId, uuid, answer, authentication));
    }

    @GetMapping("/share/{surveyId}")
    @ResponseBody
    private BaseResponse<String> share(@PathVariable("surveyId") int surveyId,
                                       Authentication authentication){

        return BaseResponse.ok(surveyService.share(surveyId, authentication));
    }
}
