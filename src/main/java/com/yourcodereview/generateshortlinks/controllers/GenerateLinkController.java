package com.yourcodereview.generateshortlinks.controllers;

import com.yourcodereview.generateshortlinks.dto.RequestOriginalLink;
import com.yourcodereview.generateshortlinks.dto.ResponseShortLink;
import com.yourcodereview.generateshortlinks.service.GetLinkService;
import com.yourcodereview.generateshortlinks.service.PrefixShortLink;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generate")
public class GenerateLinkController {
    private final GetLinkService shortLinkService;
    private final PrefixShortLink prefixShortLink;

    @PostMapping
    public ResponseShortLink generate(@RequestBody RequestOriginalLink requestOriginalLink) {
        String shortLink = shortLinkService.generateShortLink(requestOriginalLink.getOriginal());
        return ResponseShortLink.builder().link(prefixShortLink.addPrefix(shortLink)).build();
    }
}
