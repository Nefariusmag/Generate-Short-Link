package com.yourcodereview.generateshortlinks.service;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.entity.LinkEntity;
import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {

    @Autowired
    private LinkRepository linkRepository;

    public void recordUseShortLink(String shortLink) {
        LinkEntity currentStatusLinks = linkRepository.findByShortLink(shortLink);
        if (currentStatusLinks != null) {
            updateCountOfUsingShortLink(currentStatusLinks);
        }
    }

    private void updateCountOfUsingShortLink(LinkEntity currentStateEntity) {
        currentStateEntity.setCount(currentStateEntity.getCount() + 1);
        linkRepository.saveAndFlush(currentStateEntity);
    }

    public ResponseStatsLink getStatsByShortLink(String shortLink) {
        LinkEntity currentEntity = linkRepository.findByShortLink(shortLink);
        if (currentEntity == null) {
            return null;
        }
        long rank = getRankByShortLink(shortLink);
        return ResponseStatsLink.builder()
                .link(currentEntity.getShortLink())
                .original(currentEntity.getOriginalLink())
                .rank(rank)
                .count(currentEntity.getCount())
                .build();
    }

    private long getRankByShortLink(String shortLink){
        return linkRepository.getRankByShortLink(shortLink);
    }

    /**
     * Info. (page - 1) - because for user will be understandable when start from 1,
     * but for stream this start from 0
     *
     * @param page number elements that will skip
     * @param count number elements that will get from list
     * @return list with statistic use short link with filter
     */
    public List<ResponseStatsLink> getStatsByPageAndCount(long page, long count) {
        List<ResponseStatsLink> allLinksWithRank = linkRepository.getAllRank();
        return allLinksWithRank.stream()
                .skip((page - 1) * count)
                .limit(count)
                .collect(Collectors.toList());
    }
}
