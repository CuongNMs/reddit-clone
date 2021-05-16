package com.cuongnm.redditclone.repository;

import com.cuongnm.redditclone.model.Comment;
import com.cuongnm.redditclone.model.Post;
import com.cuongnm.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
