package com.tellme.tellme.domains.survey.persistence;

import com.tellme.tellme.domains.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface

QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByQuestion(String question);
}
