package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyShortUrlRepository extends JpaRepository<SurveyShortUrl, Integer> {
    SurveyShortUrl findByUrl(String url);

    SurveyShortUrl findBySurveyIdAndUserId(int surveyId, long userId);
}