package com.yourcodereview.generateshortlinks.service;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.entity.LinkEntity;
import com.yourcodereview.generateshortlinks.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final LinkService linkService;
    private final PrefixShortLink prefixShortLink;

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

    public List<ResponseStatsLink> getStatsByPage(long page, int count) {
        List<ResponseStatsLink> allLinksWithRank = linkRepository.getAllRank();
        return allLinksWithRank.stream().skip(page * count)
                .limit(count).collect(Collectors.toList());

//        return getStreamStatsLinks()
//                .sorted(Map.Entry.comparingByKey())
//                .skip((long) page * count)
//                .limit(count)
//                .map(integerEntryEntry -> ResponseStatsLink.builder()
//                        .link(prefixShortLink.addPrefix(integerEntryEntry.getValue().getKey()))
//                        .original(linkService.getOriginalLink(integerEntryEntry.getValue().getKey()))
//                        .rank(integerEntryEntry.getKey())
//                        .count(integerEntryEntry.getValue().getValue())
//                        .build())
//                .collect(Collectors.toList());
    }

    private Stream<Map.Entry<Long, Map.Entry<String, Long>>> getStreamStatsLinks() {
        Set<Map.Entry<String, Long>> allLinks =  linkService.getAllStatsLinks().entrySet();

        AtomicLong atomicLong = new AtomicLong(getUniqueCount(allLinks) + 1);
        final long[] prevLinkCount = {0};

        return allLinks
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> {
                    long rank = (prevLinkCount[0] != entry.getValue()) ? atomicLong.decrementAndGet() : atomicLong.get();
                    prevLinkCount[0] = entry.getValue();
                    return new AbstractMap.SimpleEntry<>(rank, entry);
                });
    }

    private long getUniqueCount(Set<Map.Entry<String, Long>> allLinks) {
        return allLinks.stream()
                .map(Map.Entry::getValue)
                .distinct()
                .count();
    }
}
