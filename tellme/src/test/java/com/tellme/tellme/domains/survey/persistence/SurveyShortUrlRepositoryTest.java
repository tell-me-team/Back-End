package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.SurveyShortUrl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@Sql("/sql/survey-repository-test.sql")
class SurveyShortUrlRepositoryTest {

    @Autowired
    private SurveyShortUrlRepository surveyShortUrlRepository;

    @Test
    void shortUrl로_SurveyShortUrl_데이터_조회(){
        // given
        String shortUrl = "MQ==";
        // when
        Optional<SurveyShortUrl> result = surveyShortUrlRepository.findByUrl(shortUrl);

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void SurveyShortUrl에_shortUrl조회사_데이터가_없으면_Optional_empty_를_내려준다(){
        // given
        String shortUrl = "MQ===";
        // when
        Optional<SurveyShortUrl> result = surveyShortUrlRepository.findByUrl(shortUrl);
        // then
        assertThat(result).isEmpty();
    }

    @Test
    void shortUrl_생성_되었는지_확인(){
        // given
        int surveyId = 1;
        int userId = 3;
        // when
        Optional<SurveyShortUrl> result = surveyShortUrlRepository.findBySurveyIdAndUserId(surveyId , userId);
        // then
        assertThat(result.get().getUrl()).isEqualTo("MQ==");
    }

    @Test
    void shortUrl_생성되어_있지_않으면_Optional_empty를_내려준다(){
        // given
        int surveyId = 2;
        int userId = 3;
        // when
        Optional<SurveyShortUrl> result = surveyShortUrlRepository.findBySurveyIdAndUserId(surveyId , userId);
        // then
        assertThat(result.isEmpty()).isTrue();
    }

}