package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.dto.RequestOriginalLink;
import com.yourcodereview.generateshortlinks.dto.ResponseShortLink;
import com.yourcodereview.generateshortlinks.service.GenerateLinkService;
import com.yourcodereview.generateshortlinks.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to get all requests to /generate url
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GenerateLinkController {
    private final GenerateLinkService shortLinkService;
    private final LinkService linkService;

    /**
     * Get POST request with body contains link that want to make short return short link
     *
     * @param requestOriginalLink body with JSON
     *                            {
     *                            "original": "yandex.ru"
     *                            }
     * @return JSON with short link
     * {
     *     "link": "/l/1649163001614570"
     * }
     */
    @PostMapping
    public ResponseShortLink generate(@RequestBody RequestOriginalLink requestOriginalLink) {
        String shortLink = shortLinkService.generateShortLink(requestOriginalLink.getOriginal());
        return ResponseShortLink
                .builder()
                .link(linkService.addPrefix(shortLink))
                .build();
    }
}
