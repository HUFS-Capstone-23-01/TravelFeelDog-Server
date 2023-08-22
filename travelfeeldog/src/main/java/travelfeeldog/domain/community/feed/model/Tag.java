package travelfeeldog.domain.community.feed.model;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import travelfeeldog.global.common.domain.model.BaseTimeEntity;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
