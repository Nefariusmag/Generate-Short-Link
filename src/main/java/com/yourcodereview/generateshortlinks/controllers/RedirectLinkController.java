package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.service.LinkService;
import com.yourcodereview.generateshortlinks.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/l/{some-short-name}")
public class RedirectLinkController {
    private final LinkService linkService;
    private final StatsService statsService;

    /**
     * GET request with short url to get original link.
     *
     * @param shortLink /l/adlSFNJ
     * @return redirect to original page
     */
    @GetMapping
    public ResponseEntity<String> redirectToOriginalLink(@PathVariable("some-short-name") String shortLink) {
        String originalLink = linkService.getOriginalLink(shortLink);
        if (originalLink == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Don't have an original link for " + shortLink);
        }
        statsService.recordUseShortLink(shortLink);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, originalLink)
                .build();
    }
}
