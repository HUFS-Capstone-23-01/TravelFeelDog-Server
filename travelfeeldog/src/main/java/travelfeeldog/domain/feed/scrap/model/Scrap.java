package travelfeeldog.domain.feed.scrap.model;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.member.model.Member;
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Scrap implements Persistable<String> {
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
    @CreatedDate
    private LocalDateTime createdDate;
    public Scrap(Member member , Feed feed){
        this.member = member;
        this.feed =feed;
    }

    @Override
    public boolean isNew() {
        return createdDate == null ;
    }
}