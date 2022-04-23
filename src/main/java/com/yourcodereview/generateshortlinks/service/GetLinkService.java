package com.yourcodereview.generateshortlinks.service;

import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLinkService {

    private final LinkRepository linkRepository;

    public String generateShortLink(String originalLink) {

        String shortLink = linkRepository.getShortLink(originalLink);
        if (shortLink != null) {
            return shortLink;
        }

        // TODO think about more interesting encoding
        shortLink = String.valueOf(System.currentTimeMillis() * 999);
        linkRepository.saveLink(originalLink, shortLink);
        return shortLink;
    }

    public String getOriginalLink(String shortLink) {
        return linkRepository.getOriginalLink(shortLink, false);
    }
}
