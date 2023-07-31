package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.domain.survey.application.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/{surveyId}/{userId}")
    @ResponseBody
    private ResponseEntity saveAnswer(@PathVariable("surveyId") int surveyId,
                                      @PathVariable("userId") String userId,
                                      @RequestBody SurveyDto.Answer answer){
        surveyService.saveAnswer(surveyId, userId, answer);
        return ResponseEntity.ok().build();
    }
}
