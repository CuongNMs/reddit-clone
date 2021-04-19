package com.cuongnm.redditclone.repository;

import com.cuongnm.redditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
