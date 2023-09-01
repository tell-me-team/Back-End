package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface SurveyShortUrlJpaRepository extends JpaRepository<SurveyShortUrl, Integer> {
    Optional<SurveyShortUrl> findByUrl(String url);

    Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId);
}