package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
}
