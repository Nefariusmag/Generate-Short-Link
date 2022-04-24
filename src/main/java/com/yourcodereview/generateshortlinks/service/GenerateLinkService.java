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

        // TODO think about more interesting encoding
        shortLink = String.valueOf(System.currentTimeMillis() * 999);
        linkService.saveLink(originalLink, shortLink);
        return shortLink;
    }
}
