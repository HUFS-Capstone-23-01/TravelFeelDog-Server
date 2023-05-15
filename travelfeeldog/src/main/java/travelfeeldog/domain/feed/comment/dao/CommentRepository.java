package travelfeeldog.domain.feed.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.feed.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
