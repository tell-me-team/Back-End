package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.domain.survey.application.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {

    private final SurveyService surveyService;

    @GetMapping("survey-results/{userId}/{surveyId}")
    public void getSurveyResult(@PathVariable int userId, @PathVariable int surveyId){
        surveyService.getSurveyResult(userId, surveyId);
    }

    @GetMapping("survey-results/{userId}/{surveyId}/details")
    public void getSurveyResultDetail(@PathVariable int userId, @PathVariable int surveyId){
        surveyService.getSurveyResultDetail(userId, surveyId);
    }
}
