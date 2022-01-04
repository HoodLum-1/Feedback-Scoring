package com.whereismytransport.FeedbackScoring.persistance.repository;

import com.whereismytransport.FeedbackScoring.persistance.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoresRepository extends JpaRepository<Score, Long> {
    List<Score> findByRouteIdAndSentimentScoreNotIn(String routeId, List<Integer> sentimentScores);
}