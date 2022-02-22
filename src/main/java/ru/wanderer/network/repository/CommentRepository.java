package ru.wanderer.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wanderer.network.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
