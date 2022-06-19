package com.yourcodereview.generateshortlinks.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateLinkService {

    private final LinkService linkService;
    private static final int LENGTH_ENCRYPTING = 7;

    public String generateShortLink(String originalLink) {

        String shortLink = linkService.getShortLink(originalLink);
        if (shortLink != null) {
            return shortLink;
        }

        shortLink = generateNewUniqShortLink();
        linkService.saveLink(originalLink, shortLink);
        return shortLink;
    }

    private String generateNewUniqShortLink() {
        return RandomStringUtils.randomAlphabetic(LENGTH_ENCRYPTING);
    }
}
