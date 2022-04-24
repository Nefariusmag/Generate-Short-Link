package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 */
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
     * Info: Page starts from "0"
     *
     * @param page number page that will get in the response
     * @param count number links on the page
     * @return list with statistic about rank and count for links
     */
    @GetMapping
    public List<ResponseStatsLink> getCoursesByPage(@RequestParam("page") long page, @RequestParam("count") int count) {
        // TODO add check that count < 1 or count > 100 and send error message
        if (count > 100) {
            count = 100;
        }
        return statsService.getStatsByPageAndCount(page, count);
    }
}
