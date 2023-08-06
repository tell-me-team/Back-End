package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByQuestion(String question);
}
