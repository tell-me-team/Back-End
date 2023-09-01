package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResultJpaRepository extends JpaRepository<SurveyResult, Integer> {
    SurveyResult findByTypeNumber(int typeNumber);
}
