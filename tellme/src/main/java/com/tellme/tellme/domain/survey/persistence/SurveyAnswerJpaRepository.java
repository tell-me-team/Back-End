package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerJpaRepository extends JpaRepository<SurveyAnswer, Integer> {
}
