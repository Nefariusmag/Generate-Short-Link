package com.yourcodereview.generateshortlinks.controllers;

import com.google.gson.Gson;
import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;
    private static final String VALIDATION_REQUEST_MESSAGE =
            "Number page couldn't be a few 0, Number count must be between 0 and 100";

    /**
     * Request to get rank of some short links.
     *
     * @param shortLink some link in url request
     * @return statistic with original link, short link, rank and count of using
     */
    @GetMapping("/{some-short-name}")
    public ResponseStatsLink getStatsByShortLink(@PathVariable("some-short-name") String shortLink) {
        log.info("Request to get statistic for link {}", shortLink);
        return statsService.getStatsByShortLink(shortLink);
    }

    /**
     * Request to get page with statistic have some numbers elements.
     *
     * @param page number page that will get in the response
     * @param count number links on the page
     * @return JSON with list with statistic about rank and count for links
     */
    @GetMapping
    public ResponseEntity<String> getCoursesByPage(
            @RequestParam("page") @Min(value = 1, message = VALIDATION_REQUEST_MESSAGE) long page,
            @RequestParam("count") @Min(value = 1, message = VALIDATION_REQUEST_MESSAGE)
                                   @Max(value = 100, message = VALIDATION_REQUEST_MESSAGE) long count) {
        log.info("Get request from page: {} count: {}", page, count);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Gson().toJson(statsService.getStatsByPageAndCount(page, count)));
    }
}
