package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.SurveyShortUrl;

import java.util.Optional;

public interface SurveyShortUrlRepository {
    Optional<SurveyShortUrl> findByUrl(String url);

    Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId);

    SurveyShortUrl save(SurveyShortUrl shortUrl);

    long count();
}
