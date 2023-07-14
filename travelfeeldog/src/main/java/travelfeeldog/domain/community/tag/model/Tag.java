package travelfeeldog.domain.community.tag.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD:travelfeeldog/src/main/java/travelfeeldog/domain/feed/tag/model/Tag.java
import travelfeeldog.domain.feed.feed.model.FeedTag;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;
=======
import travelfeeldog.domain.community.feed.model.FeedTag;
import travelfeeldog.global.common.model.BaseTimeEntity;
>>>>>>> develop:travelfeeldog/src/main/java/travelfeeldog/domain/community/tag/model/Tag.java

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_content")
    private String tagContent;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FeedTag> feedTags = new ArrayList<>();

    private Tag(String tagContent) { this.tagContent = tagContent; }

    public static Tag create(String tagContent) { return new Tag(tagContent); }
}
