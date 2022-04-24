package com.yourcodereview.generateshortlinks.service;

import com.yourcodereview.generateshortlinks.entity.LinkEntity;
import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    private final Map<String, String> links = new HashMap<>();
    private final Map<String, Long> stats = new HashMap<>();

    public String getShortLink(String originalLink) {
        String shortLink = null;
        if (linkRepository.findByOriginalLink(originalLink) != null) {
            shortLink = linkRepository.findByOriginalLink(originalLink).getShortLink();
        }
        return shortLink;
    }

    public String getOriginalLink(String shortLink) {
        return linkRepository.findByShortLink(shortLink).getOriginalLink();
    }

    public void saveLink(String originalLink, String shortLink) {
        linkRepository.save(LinkEntity
                .builder()
                .originalLink(originalLink)
                .shortLink(shortLink)
                .count(0L).build());
    }

    public Map<String, Long> getAllStatsLinks() {
        return stats;
    }
}
