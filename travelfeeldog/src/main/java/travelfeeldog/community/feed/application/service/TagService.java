package travelfeeldog.community.feed.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.community.feed.infrastructure.TagRepository;
import travelfeeldog.community.feed.domain.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> findTagListByTagContents(List<String> tagContents) {
        List<Tag> tags = new ArrayList<>();
        for (String tagContent : tagContents) {
            Optional<Tag> tag = tagRepository.findByTagContent(tagContent);
            if (tag.isPresent()) {
                tags.add(tag.get());
            }
        }
        return tags;
    }

    public List<Tag> makeTagListByTagContents(List<String> tagContents) {
        List<Tag> tags = new ArrayList<>();
        for (String tagContent : tagContents) {
            Optional<Tag> tag = tagRepository.findByTagContent(tagContent);
            if (tag.isPresent()) {
                tags.add(tag.get());
            } else {
                tags.add(tagRepository.save(tagContent));
            }
        }
        return tags;
    }

}
