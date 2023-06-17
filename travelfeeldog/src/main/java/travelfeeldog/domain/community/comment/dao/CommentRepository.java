package travelfeeldog.domain.community.comment.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.community.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByFeedId(Long feedId);
}
