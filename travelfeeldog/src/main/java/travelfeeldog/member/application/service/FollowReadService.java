package travelfeeldog.member.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.member.domain.model.Follow;
import travelfeeldog.member.domain.FollowRepository;

@RequiredArgsConstructor
@Service
public class FollowReadService {
    final private FollowRepository followRepository;

    public List<Follow> getFollowings(Long memberId) {
        return followRepository.findAllByFromMemberId(memberId);
    }

    public List<Follow> getFollowers(Long memberId) {
        return followRepository.findAllByToMemberId(memberId);
    }

}
