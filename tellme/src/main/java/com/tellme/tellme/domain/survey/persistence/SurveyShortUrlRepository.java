package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyShortUrlRepository extends JpaRepository<SurveyShortUrl, Integer> {
    Optional<SurveyShortUrl> findByUrl(String url);

    Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId);
}