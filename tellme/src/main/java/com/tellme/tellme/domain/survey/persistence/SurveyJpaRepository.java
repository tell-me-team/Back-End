package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepository extends JpaRepository<Survey, Integer> {

}
