package com.tellme.tellme.domains.survey.infrastructure;

import com.tellme.tellme.domains.survey.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResultJpaRepository extends JpaRepository<SurveyResult, Integer> {
    SurveyResult findByTypeNumber(int typeNumber);
}
