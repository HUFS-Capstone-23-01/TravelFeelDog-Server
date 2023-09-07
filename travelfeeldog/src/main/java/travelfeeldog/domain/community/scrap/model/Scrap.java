package travelfeeldog.domain.community.scrap.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.community.feed.model.Feed;

@Setter
@Getter
@Entity
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id", length = 100)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "feed_id")
    private Feed feed;
    protected Scrap(){

    }
    public Scrap(Member member , Feed feed){
        this.member = member;
        this.feed =feed;
    }
    public boolean checkMember(Member member) {
        return this.member.equals(member);
    }

}
