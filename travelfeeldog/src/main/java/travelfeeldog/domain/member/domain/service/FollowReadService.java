package travelfeeldog.domain.member.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.member.domain.model.Follow;
import travelfeeldog.domain.member.infrastructure.FollowRepository;

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
