package com.yourcodereview.generateshortlinks.controllers;

import com.google.gson.Gson;
import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * TODO
 */
@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    /**
     * Request to get rank of some short links
     *
     * @param shortLink some link in url request
     * @return statistic with original link, short link, rank and count of using
     */
    @GetMapping("/{some-short-name}")
    public ResponseStatsLink getStatsByShortLink(@PathVariable("some-short-name") String shortLink) {
        return statsService.getStatsByShortLink(shortLink);
    }

    /**
     * Request to get page with statistic have some numbers elements
     *
     * @param page number page that will get in the response
     * @param count number links on the page
     * @return JSON with list with statistic about rank and count for links
     */
    @GetMapping
    public ResponseEntity<String> getCoursesByPage(@RequestParam("page") long page,
                                                                    @RequestParam("count") long count) {
        log.info("Get request from page: {} count: {}", page, count);
        if (!isCorrectNumberCount(count)) {
            log.error("Request has not acceptable count: {}", count);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Count can be only between 1 and 100");
        }
        if (!isCorrectNumberPage(page)) {
            log.error("Request has not acceptable page: {}", count);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Page starts from 1");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Gson().toJson(statsService.getStatsByPageAndCount(page, count)));
    }

    private boolean isCorrectNumberCount(long number) {
        return number >= 1 && number <= 100;
    }

    private boolean isCorrectNumberPage(long number) {
        return number >= 1;
    }
}
