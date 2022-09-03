package com.yourcodereview.generateshortlinks.dto;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class ResponseStatsLink {
    private String link;
    private String original;
    private Long count;
    private Long rank;
}
