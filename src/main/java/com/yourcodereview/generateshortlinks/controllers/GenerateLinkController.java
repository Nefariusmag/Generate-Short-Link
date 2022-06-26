package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.dto.RequestOriginalLink;
import com.yourcodereview.generateshortlinks.dto.ResponseShortLink;
import com.yourcodereview.generateshortlinks.service.GenerateLinkService;
import com.yourcodereview.generateshortlinks.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GenerateLinkController {
    private final GenerateLinkService shortLinkService;
    private final LinkService linkService;

    /**
     * POST request with body contains link that want to make short return short link.
     *
     * @param requestOriginalLink body with JSON
     *                            {
     *                            "original": "yandex.ru"
     *                            }
     * @return JSON with short link
     * {
     *     "link": "/l/adlSFNJ"
     * }
     */
    @PostMapping
    public ResponseShortLink generate(@RequestBody RequestOriginalLink requestOriginalLink) {
        log.info("Request to generate short link for original link {}", requestOriginalLink.getOriginal());
        String shortLink = shortLinkService.generateShortLink(requestOriginalLink.getOriginal());
        return ResponseShortLink
                .builder()
                .link(linkService.addPrefix(shortLink))
                .build();
    }
}
