package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyShortUrlRepository;
import com.tellme.tellme.domain.survey.entity.SurveyShortUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurveyShortUrlRepositoryImpl implements SurveyShortUrlRepository {

    private final SurveyShortUrlJpaRepository surveyShortUrlJpaRepository;

    @Override
    public Optional<SurveyShortUrl> findByUrl(String url) {
        return surveyShortUrlJpaRepository.findByUrl(url);
    }

    @Override
    public Optional<SurveyShortUrl> findBySurveyIdAndUserId(int surveyId, int userId) {
        return surveyShortUrlJpaRepository.findBySurveyIdAndUserId(surveyId, userId);
    }

    @Override
    public SurveyShortUrl save(SurveyShortUrl shortUrl) {
        return surveyShortUrlJpaRepository.save(shortUrl);
    }

    @Override
    public long count() {
        return surveyShortUrlJpaRepository.count();
    }
}
