package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.service.GetLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/l/{some-short-name}")
public class RedirectLinkController {
    private final GetLinkService shortLinkService;

    @GetMapping
    public ResponseEntity<Void> redirectToOriginalLink(@PathVariable("some-short-name") String shortLink) {
        String originalLink = shortLinkService.getOriginalLink(shortLink);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, originalLink)
                .build();
    }
}
