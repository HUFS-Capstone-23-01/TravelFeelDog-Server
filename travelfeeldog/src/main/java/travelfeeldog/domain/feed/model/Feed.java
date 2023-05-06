package travelfeeldog.domain.feed.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.global.common.model.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id", length = 64)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "feed_like", length = 100)
    private int feedLike;

    @Column(name = "feed_scrap", length = 100)
    private int feedScrap;

    @Column(name = "feed_title", length = 64)
    private String feedTitle;

    @Column(name = "feed_body", length = 500)
    private String feedBody;

}
