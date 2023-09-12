package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.SurveyShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyShortUrlRepository extends JpaRepository<SurveyShortUrl, Integer> {
    Optional<SurveyShortUrl> findByUrl(String url);

    Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId);
}