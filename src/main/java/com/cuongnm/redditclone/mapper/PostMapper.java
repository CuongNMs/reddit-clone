package com.cuongnm.redditclone.mapper;

import com.cuongnm.redditclone.dto.PostResponse;
import com.cuongnm.redditclone.model.Post;
import com.cuongnm.redditclone.model.Subreddit;
import com.cuongnm.redditclone.model.User;
import com.cuongnm.redditclone.repository.CommentRepository;
import com.cuongnm.redditclone.repository.VoteRepository;
import com.cuongnm.redditclone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "postName", source = "postName")
    public abstract Post map(String description, String postName, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "subredditName", source = "post.subreddit.name")
    @Mapping(target = "username", source = "post.user.username")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

}
