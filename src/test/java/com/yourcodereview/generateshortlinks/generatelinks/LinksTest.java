package com.yourcodereview.generateshortlinks.generatelinks;

import com.yourcodereview.generateshortlinks.service.LinkService;
import org.junit.jupiter.api.Test;


import static com.yourcodereview.generateshortlinks.BaseIT.SHORT_LINK;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LinksTest {

    @Test
    void addPrefixTest(){
        LinkService linkService = new LinkService();
        assertEquals("/l/" + SHORT_LINK, linkService.addPrefix(SHORT_LINK));
    }
}
