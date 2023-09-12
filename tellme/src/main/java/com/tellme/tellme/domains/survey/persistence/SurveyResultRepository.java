package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Integer> {
    SurveyResult findByTypeNumber(int typeNumber);
}
