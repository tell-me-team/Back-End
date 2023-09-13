package com.tellme.tellme.domains.survey.infrastructure;

import com.tellme.tellme.domains.survey.entity.SurveyAnswer;
import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerJpaRepository extends JpaRepository<SurveyAnswer, Integer> {
    List<SurveyAnswer> findBySurveyCompletion(SurveyCompletion surveyCompletion);
}
