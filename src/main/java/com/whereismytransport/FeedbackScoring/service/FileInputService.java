package com.whereismytransport.FeedbackScoring.service;

import com.whereismytransport.FeedbackScoring.persistance.entity.ReferenceData;
import com.whereismytransport.FeedbackScoring.persistance.entity.Score;
import com.whereismytransport.FeedbackScoring.persistance.repository.ReferenceDataRepository;
import com.whereismytransport.FeedbackScoring.persistance.repository.ScoresRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class FileInputService {
    private ReferenceDataRepository referenceDataRepository;
    private ScoresRepository scoresRepository;

    public void readScoresDataFile() {
        List<Score> scores = new ArrayList<>();
        var scoresDataFile = "scores.txt";
        var resourceDataUrl = this.getClass().getClassLoader().getResource(scoresDataFile);

        try {
            Files.readAllLines(new File(Objects.requireNonNull(resourceDataUrl).getFile()).toPath()).forEach(line -> {
                var scoreArray = line.split(" ");
                Score score = new Score();
                score.setDateAdded(LocalDate.parse(scoreArray[0],
                        DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                score.setDayOfWeek(score.getDateAdded().getDayOfWeek().name());
                score.setRouteId(scoreArray[1]);
                score.setSentimentScore(Integer.parseInt(scoreArray[2]));
                scores.add(score);
                log.info("Route [{}]", score);
            });
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        if (!scores.isEmpty()){
            scoresRepository.saveAll(scores);
        }
    }

    public void readRefData() {
        List<ReferenceData> referenceDataList = new ArrayList<>();
        var referenceDataFile = "reference-data.txt";
        var resourceDataUrl = this.getClass().getClassLoader().getResource(referenceDataFile);

        try {
            Files.readAllLines(new File(Objects.requireNonNull(resourceDataUrl).getFile()).toPath()).forEach(line -> {
                var referenceArray = line.replaceFirst(" ", " -").split(";| -");
                ReferenceData referenceData = new ReferenceData(referenceArray[0], referenceArray[1], referenceArray[2], referenceArray[3]);
                referenceDataList.add(referenceData);
            });
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }

        if (!referenceDataList.isEmpty()){
            referenceDataRepository.saveAll(referenceDataList);
        }
    }
}
