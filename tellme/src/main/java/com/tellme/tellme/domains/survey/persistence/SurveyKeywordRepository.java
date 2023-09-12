package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.SurveyResultKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyKeywordRepository extends JpaRepository<SurveyResultKeyword, Integer> {
}
