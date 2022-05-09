package com.yourcodereview.generateshortlinks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateLinkService {

    private final LinkService linkService;

    public String generateShortLink(String originalLink) {

        String shortLink = linkService.getShortLink(originalLink);
        // TODO check it is not just ""
        if (shortLink != null) {
            return shortLink;
        }

        shortLink = generateNewUniqShortLink();
        linkService.saveLink(originalLink, shortLink);
        return shortLink;
    }

    // TODO think about more interesting encoding
    private String generateNewUniqShortLink() {
        return String.valueOf(System.currentTimeMillis() * 999);
    }
}
