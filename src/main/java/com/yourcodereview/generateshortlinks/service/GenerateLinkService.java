package com.yourcodereview.generateshortlinks.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateLinkService {

    private static final int LENGTH_ENCRYPTING = 7;
    private final LinkService linkService;

    public String generateShortLink(String originalLink) {

        return Optional.ofNullable(linkService.getShortLink(originalLink))
                .orElseGet(() -> generateNewUniqShortLink(originalLink));
    }

    private String generateNewUniqShortLink(String originalLink) {
        String shortLink = RandomStringUtils.randomAlphabetic(LENGTH_ENCRYPTING);
        linkService.saveLink(originalLink, shortLink);
        return shortLink;
    }
}
