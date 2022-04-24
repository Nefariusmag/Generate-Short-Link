package com.yourcodereview.generateshortlinks.repository;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import com.yourcodereview.generateshortlinks.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    LinkEntity findByOriginalLink(String originalLink);

    LinkEntity findByShortLink(String shortLink);

    @Query(value = "select rank from " +
            "(select short_link, rank() over (order by count desc) rank from links) short_links_rank " +
            "where short_link = ?1", nativeQuery = true)
    long getRankByShortLink(String shortLink);

    @Query(name = "getAllRank", nativeQuery = true)
    List<ResponseStatsLink> getAllRank();
}
