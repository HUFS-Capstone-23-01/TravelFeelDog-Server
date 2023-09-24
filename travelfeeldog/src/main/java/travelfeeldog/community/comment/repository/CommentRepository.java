package travelfeeldog.community.comment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.community.comment.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByFeedId(Long feedId);
}
