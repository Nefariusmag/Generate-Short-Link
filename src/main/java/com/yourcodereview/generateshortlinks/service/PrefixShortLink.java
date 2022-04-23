package com.yourcodereview.generateshortlinks.service;

import org.springframework.stereotype.Service;

@Service
public class PrefixShortLink {
    private static final String PREFIX_SHORT_LINK = "/l/";

    public String addPrefix(String link) {
        return PREFIX_SHORT_LINK + link;
    }
}
