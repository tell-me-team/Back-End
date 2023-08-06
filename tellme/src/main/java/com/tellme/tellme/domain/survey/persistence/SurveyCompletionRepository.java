package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyCompletionRepository extends JpaRepository<SurveyCompletion, Integer> {
    SurveyCompletion findByUniqueId(String uniqueId);

    List<SurveyCompletion> findByUser(User user);
}
