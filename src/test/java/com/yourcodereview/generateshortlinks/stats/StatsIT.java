package com.yourcodereview.generateshortlinks.stats;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.generatelinks.BaseIT;
import com.yourcodereview.generateshortlinks.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class StatsIT extends BaseIT {

    @Autowired
    private StatsService statsService;

    @Test
    @Sql("/db/initial_data.sql")
    void getStatsByShortLinkTest(){
        log.info("Start test to get statistic by short link.");
        ResponseStatsLink currentStats = ResponseStatsLink.builder()
                .link(SHORT_LINK)
                .original(ORIGINAL_LINK)
                .rank(1L)
                .count(0L)
                .build();
        assertEquals(currentStats, statsService.getStatsByShortLink(SHORT_LINK));

        assertNull(statsService.getStatsByShortLink(FAKE_SHORT_LINK));
    }

    @Test
    @Sql("/db/initial_data.sql")
    void writeUseShortLinkTest(){
        log.info("Start test to write new use short link.");

        long currentCount = statsService.getStatsByShortLink(SECOND_SHORT_LINK).getCount();
        int numberUseShortLink = 10;

        for (int i = 0; i < numberUseShortLink; i++) {
            statsService.recordUseShortLink(SECOND_SHORT_LINK);
        }
        ResponseStatsLink newCurrentStats = ResponseStatsLink.builder()
                .link(SECOND_SHORT_LINK)
                .original(SECOND_ORIGINAL_LINK)
                .rank(1L)
                .count(currentCount + numberUseShortLink)
                .build();
        assertEquals(newCurrentStats, statsService.getStatsByShortLink(SECOND_SHORT_LINK));
    }

    @Test
    @Sql("/db/initial_data.sql")
    void getStatisticForPageAndCountTest(){
        log.info("Start test to write new use short link.");
        List<ResponseStatsLink> theFirstPageList = new LinkedList<>(
                Collections.singleton(ResponseStatsLink.builder()
                        .link(SHORT_LINK)
                        .original(ORIGINAL_LINK)
                        .count(0L)
                        .rank(1L)
                        .build()));
        assertEquals(theFirstPageList, statsService.getStatsByPageAndCount(1, 1));

        List<ResponseStatsLink> theSecondPageList = new LinkedList<>(
                Collections.singleton(ResponseStatsLink.builder()
                        .link(SECOND_SHORT_LINK)
                        .original(SECOND_ORIGINAL_LINK)
                        .count(0L)
                        .rank(1L)
                        .build()));
        assertEquals(theSecondPageList, statsService.getStatsByPageAndCount(2, 1));

        List<ResponseStatsLink> allPagesList = new LinkedList<>();
        allPagesList.add(ResponseStatsLink.builder()
                .link(SHORT_LINK)
                .original(ORIGINAL_LINK)
                .count(0L)
                .rank(1L)
                .build());
        allPagesList.add(ResponseStatsLink.builder()
                .link(SECOND_SHORT_LINK)
                .original(SECOND_ORIGINAL_LINK)
                .count(0L)
                .rank(1L)
                .build());
        assertEquals(allPagesList, statsService.getStatsByPageAndCount(1, 2));
        assertEquals(new ArrayList<>(), statsService.getStatsByPageAndCount(2, 2));
        assertThrows(IllegalArgumentException.class, () -> statsService.getStatsByPageAndCount(0, 1));
    }
}
