package com.whereismytransport.FeedbackScoring.web;

import com.whereismytransport.FeedbackScoring.service.FileInputService;
import com.whereismytransport.FeedbackScoring.service.FileOutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class RoutesController {

    private FileInputService fileInputService;
    private FileOutService fileOutService;

    @PostMapping(path = "/consume-files")
    public void consumeFiles() {
        log.info("consuming files...");
        fileInputService.readRefData();
        fileInputService.readScoresDataFile();
    }

    @GetMapping(path = "/produce-results")
    public ResponseEntity produceResults() {
        log.info("Producing Results");
        ByteArrayResource byteArrayResource = new ByteArrayResource(fileOutService.getFileBody());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=results.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(byteArrayResource.contentLength())
                .body(byteArrayResource);
    }
}
