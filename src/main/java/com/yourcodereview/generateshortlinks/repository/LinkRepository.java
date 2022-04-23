package com.yourcodereview.generateshortlinks.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class LinkRepository {

    private final Map<String, String> links = new HashMap<>();
    private final Map<String, Long> stats = new HashMap<>();

    public String getShortLink(String originalLink) {
        return links.get(originalLink);
    }

    public String getOriginalLink(String shortLink, boolean fromStat) {
        if (!fromStat) {
            stats.merge(shortLink, 1L, Long::sum);
        }
        return links.get(shortLink);
    }

    public void saveLink(String originalLink, String shortLink) {
        links.put(originalLink, shortLink);
        links.put(shortLink, originalLink);
    }

    public Map<String, Long> getAllStatsLinks() {
        return stats;
    }
}
