package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

}
