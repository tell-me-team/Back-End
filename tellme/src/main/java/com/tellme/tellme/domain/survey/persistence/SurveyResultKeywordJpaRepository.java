package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyResultKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResultKeywordJpaRepository extends JpaRepository<SurveyResultKeyword, Integer> {
}
