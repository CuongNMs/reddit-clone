package com.cuongnm.redditclone.repository;

import com.cuongnm.redditclone.model.Post;
import com.cuongnm.redditclone.model.User;
import com.cuongnm.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
