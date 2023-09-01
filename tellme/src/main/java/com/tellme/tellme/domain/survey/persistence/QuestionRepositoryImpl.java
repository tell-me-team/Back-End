package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.QuestionRepository;
import com.tellme.tellme.domain.survey.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public Question findByQuestion(String question) {
        return questionJpaRepository.findByQuestion(question);
    }

    @Override
    public Optional<Question> findById(int question) {
        return questionJpaRepository.findById(question);
    }
}
