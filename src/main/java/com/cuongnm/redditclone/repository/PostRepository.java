package com.cuongnm.redditclone.repository;

import com.cuongnm.redditclone.model.Post;
import com.cuongnm.redditclone.model.Subreddit;
import com.cuongnm.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
