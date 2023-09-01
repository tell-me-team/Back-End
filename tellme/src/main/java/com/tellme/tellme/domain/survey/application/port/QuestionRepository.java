package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.Question;

import java.util.Optional;

public interface QuestionRepository {
    Question findByQuestion(String question);

    Optional<Question> findById(int question);
}
