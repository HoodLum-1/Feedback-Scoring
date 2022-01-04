package com.whereismytransport.FeedbackScoring.service;

import com.whereismytransport.FeedbackScoring.persistance.entity.ReferenceData;
import com.whereismytransport.FeedbackScoring.persistance.entity.Score;
import com.whereismytransport.FeedbackScoring.persistance.repository.ReferenceDataRepository;
import com.whereismytransport.FeedbackScoring.persistance.repository.ScoresRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FileInputServiceTest {
    private static final long id =5;

    @Autowired
    private FileInputService fileInputService;

    @Autowired
    ScoresRepository scoresRepository;

    @Autowired
    ReferenceDataRepository referenceDataRepository;

    @Test
    void testFindScoreById() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date =  "2021/11/15";
        LocalDate localDate = LocalDate.parse(date, formatter);
        fileInputService.readScoresDataFile();
        Optional<Score> optionalScore = scoresRepository.findById(id);
        Assertions.assertTrue(optionalScore.isPresent());
        Score foundScore = optionalScore.get();
        Assertions.assertEquals(id,foundScore.getScoreId());
        Assertions.assertEquals(localDate, foundScore.getDateAdded());
        Assertions.assertEquals("MONDAY", foundScore.getDayOfWeek());
        Assertions.assertEquals("route_68a711e1-440d-426c-a6e9-a119d98502d2", foundScore.getRouteId());
        Assertions.assertEquals(8, foundScore.getSentimentScore());
    }

    @Test
    void testScoreIdNotNull() {
        fileInputService.readScoresDataFile();
        Score score = new Score();
        score.setDateAdded(LocalDate.parse("2022/01/03",
                DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        score.setRouteId("route_cc4722b0-db3b-4732-9e5f-456325d59ffe");
        score.setDayOfWeek("Monday");
        score.setSentimentScore(7);

        scoresRepository.save(score);

        Assertions.assertNotNull(score.getScoreId());
    }

    @Test
    void testCreateScore() {
        Score score = new Score();
        score.setDateAdded(LocalDate.parse("2022/01/03",
                DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        score.setRouteId("route_cc4722b0-db3b-4732-9e5f-456325d59ffe");
        score.setDayOfWeek("Monday");
        score.setSentimentScore(7);

        scoresRepository.save(score);
        Optional<Score> optionalScore = scoresRepository.findById((long) 10000);

        Assertions.assertTrue(optionalScore.isPresent());
        Score savedScore = optionalScore.get();
        Assertions.assertNotNull(score);
        Assertions.assertEquals(savedScore.getScoreId(), score.getScoreId());
        Assertions.assertEquals(savedScore.getDateAdded(), score.getDateAdded());
        Assertions.assertEquals(savedScore.getDayOfWeek(), score.getDayOfWeek());
        Assertions.assertEquals(savedScore.getRouteId(), score.getRouteId());
        Assertions.assertEquals(savedScore.getSentimentScore(), score.getSentimentScore());
    }

    @Test
    public void testFindByRouteIdAndSentimentScoreNotIn() {
        fileInputService.readScoresDataFile();
        Score score = new Score();
        scoresRepository.findByRouteIdAndSentimentScoreNotIn(score.getRouteId(), List.of(0,10));
        org.assertj.core.api.Assertions.assertThat(scoresRepository.findAll()).isNotEmpty();
    }

    @Test
    public void testDeleteAllScores() {
        fileInputService.readScoresDataFile();
        scoresRepository.deleteAll();
        org.assertj.core.api.Assertions.assertThat(scoresRepository.findAll()).isEmpty();
    }

    @Test
    void testFinalAllScores() {
        Assertions.assertNotNull(scoresRepository.findAll());
    }

    @Test
    void testCreateReferenceDataItem() {
        ReferenceData referenceData = new ReferenceData("route_cc4722b0-db3b-4732-9e5f-456325d59ffe","METRO","Ciudad Azteca","Buenavista");
        referenceDataRepository.save(referenceData);
        Optional<ReferenceData> optionalReferenceData = referenceDataRepository.findById("route_cc4722b0-db3b-4732-9e5f-456325d59ffe");

        Assertions.assertTrue(optionalReferenceData.isPresent());
        ReferenceData savedReferenceData = optionalReferenceData.get();
        Assertions.assertNotNull(referenceData);
        Assertions.assertEquals(savedReferenceData.getAgencyId(), referenceData.getAgencyId());
        Assertions.assertEquals(savedReferenceData.getMetro(), referenceData.getMetro());
        Assertions.assertEquals(savedReferenceData.getRouteName(), referenceData.getRouteName());
    }

    @Test
    void testFinalAllReferenceData() {
        Assertions.assertNotNull(referenceDataRepository.findAll());
    }

    @Test
    void testDeleteReferenceDataItem() {
        ReferenceData referenceData = new ReferenceData("route_cc4722b0-db3b-4732-9e5f-456325d59ffe","METRO","Ciudad Azteca","Buenavista");
        referenceDataRepository.save(referenceData);
        Assertions.assertNotNull(referenceData);
        referenceDataRepository.delete(referenceData);
    }

    @Test
    void testDeleteAllReferenceData() {
        referenceDataRepository.deleteAll();
        org.assertj.core.api.Assertions.assertThat(referenceDataRepository.findAll()).isEmpty();
    }
}