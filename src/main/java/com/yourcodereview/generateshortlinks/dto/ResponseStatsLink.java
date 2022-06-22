package com.yourcodereview.generateshortlinks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStatsLink {
    private String link;
    private String original;
    private Long count;
    private Long rank;
}
