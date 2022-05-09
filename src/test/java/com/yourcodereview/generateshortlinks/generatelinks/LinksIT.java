package com.yourcodereview.generateshortlinks.generatelinks;

import com.yourcodereview.generateshortlinks.service.GenerateLinkService;
import com.yourcodereview.generateshortlinks.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LinksIT extends BaseIT {

    @Autowired
    private GenerateLinkService generateLinkService;
    @Autowired
    private LinkService linkService;

    @Test
    @Sql("/db/initial_data.sql")
    void generateShortLinkByOriginalLinkTest(){
        log.info("Start test to generate short link.");
        assertEquals(SHORT_LINK, generateLinkService.generateShortLink(ORIGINAL_LINK));

        String newShortLink = generateLinkService.generateShortLink(FRESH_ORIGINAL_LINK);
        assertEquals(16, newShortLink.length());
        assertNotEquals(SHORT_LINK, newShortLink);
    }

    @Test
    @Sql("/db/initial_data.sql")
    void getOriginalLinkByShortLinkTest(){
        log.info("Start test to get original link by short link.");
        assertEquals(ORIGINAL_LINK, linkService.getOriginalLink(SHORT_LINK));
        assertNull(linkService.getOriginalLink(FAKE_SHORT_LINK));
    }

    @Test
    @Sql("/db/initial_data.sql")
    void saveNewShortLinkTest(){
        log.info("Start test to save new short link.");
        assertNull(linkService.getOriginalLink(FRESH_SHORT_LINK));
        linkService.saveLink(FRESH_ORIGINAL_LINK, FRESH_SHORT_LINK);
        assertEquals(FRESH_ORIGINAL_LINK, linkService.getOriginalLink(FRESH_SHORT_LINK));
    }
}
