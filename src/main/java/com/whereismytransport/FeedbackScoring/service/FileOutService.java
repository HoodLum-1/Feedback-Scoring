package com.whereismytransport.FeedbackScoring.service;


import com.whereismytransport.FeedbackScoring.dto.Result;
import com.whereismytransport.FeedbackScoring.persistance.entity.Score;
import com.whereismytransport.FeedbackScoring.persistance.repository.ReferenceDataRepository;
import com.whereismytransport.FeedbackScoring.persistance.repository.ScoresRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FileOutService {
    private ScoresRepository scoresRepository;
    private ReferenceDataRepository referenceDataRepository;

    private List<Result> getResults() {
        List<Result> results = new ArrayList<>();
        referenceDataRepository.findAll().forEach(referenceData -> {
            List<Score> scores = scoresRepository.findByRouteIdAndSentimentScoreNotIn(referenceData.getRouteId(), List.of(0,10));
            dayOfWeeks(scores).forEach(dayOfWeek -> {
                BigDecimal averageScore = calculateAverageScore(scores, dayOfWeek);
                results.add(new Result(referenceData.getAgencyId(), referenceData.getRouteName(), dayOfWeek, averageScore));
            });
        });
        results.sort(Comparator.comparing(Result::getAgencyId).thenComparing(Result::getRouteName).thenComparing(Result::getAverageScore).reversed());
        return results;
    }

    private Set<String> dayOfWeeks(List<Score> scores) {
        return scores.stream().map(Score::getDayOfWeek).collect(Collectors.toSet());
    }

    private BigDecimal calculateAverageScore(List<Score> scores, String dayOfWeek){
        OptionalDouble averageScore = scores.stream().filter(score -> score.getDayOfWeek().equalsIgnoreCase(dayOfWeek)).mapToInt(Score::getSentimentScore).average();

        return BigDecimal.valueOf(averageScore.orElseThrow()).setScale(2, RoundingMode.DOWN);
    }

    public byte[] getFileBody() {
        return getResults().stream().map(Result::toString).collect(Collectors.joining("\n")).getBytes(StandardCharsets.UTF_8);
    }

}
