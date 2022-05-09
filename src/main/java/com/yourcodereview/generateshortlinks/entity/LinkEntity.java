package com.yourcodereview.generateshortlinks.entity;

import com.yourcodereview.generateshortlinks.dto.ResponseStatsLink;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "links")

@NamedNativeQuery(
        name = "getAllRank",
        query = "select * from " +
                        "(select short_link, original_link, count, " +
                        "rank() over (order by count desc) rank from links) short_links_rank",
        resultSetMapping = "response_stats_link_dto"
)
@SqlResultSetMapping(
        name = "response_stats_link_dto",
        classes = @ConstructorResult(
                targetClass = ResponseStatsLink.class,
                columns = {
                        @ColumnResult(name = "short_link", type = String.class),
                        @ColumnResult(name = "original_link", type = String.class),
                        @ColumnResult(name = "count", type = Long.class),
                        @ColumnResult(name = "rank", type = Long.class)
                }
        )
)
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "short_link", unique = true, length = 16)
    private String shortLink;

    @Column(name = "original_link", unique = true, length = 555)
    private String originalLink;

    @Column(name = "count")
    private Long count;

}
