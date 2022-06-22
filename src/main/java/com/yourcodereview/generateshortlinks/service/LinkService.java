package com.yourcodereview.generateshortlinks.service;

import com.yourcodereview.generateshortlinks.entity.LinkEntity;
import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    private static final String PREFIX_SHORT_LINK = "/l/";

    @Autowired
    private LinkRepository linkRepository;

    protected String getShortLink(String originalLink) {
        String shortLink = null;
        if (linkRepository.findByOriginalLink(originalLink) != null) {
            shortLink = linkRepository.findByOriginalLink(originalLink).getShortLink();
        }
        return shortLink;
    }

    public String getOriginalLink(String shortLink) {
        if (linkRepository.findByShortLink(shortLink) == null) {
            return null;
        }
        return linkRepository.findByShortLink(shortLink).getOriginalLink();
    }

    public void saveLink(String originalLink, String shortLink) {
        linkRepository.save(LinkEntity.builder()
                .originalLink(originalLink)
                .shortLink(shortLink)
                .count(0L)
                .build());
    }

    public String addPrefix(String link) {
        return PREFIX_SHORT_LINK + link;
    }
}
