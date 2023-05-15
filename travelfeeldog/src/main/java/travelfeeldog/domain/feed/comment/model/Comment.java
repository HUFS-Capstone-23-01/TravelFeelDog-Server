package travelfeeldog.domain.feed.comment.model;

import lombok.Getter;
import lombok.Setter;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.global.common.model.BaseTimeEntity;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", length = 100)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "comment_content", length = 500)
    private String content;
}
