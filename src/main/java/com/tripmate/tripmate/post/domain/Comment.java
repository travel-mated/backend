package com.tripmate.tripmate.post.domain;


import com.tripmate.tripmate.common.BaseTimeEntity;
import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_COMMENT_ID")
    private Long id;

    private Long postId;

    @ManyToOne
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> childComment = new ArrayList<>();

    @ManyToOne
    private User user;

    @Column(length = 500, nullable = false)
    private String contents;

    @Builder
    public Comment(Long postId, Comment parentComment, List<Comment> childComment, User user, String contents) {
        this.postId = postId;
        this.parentComment = parentComment;
        this.user = user;
        this.childComment = childComment;
        this.contents = contents;
    }

    public Comment(Long postId, User user, String contents) {
        this.postId = postId;
        this.user = user;
        this.contents = contents;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public void addCommentReply(Comment reply) {
        this.childComment.add(reply);
        reply.setParentComment(this);
    }

    public void updateContent(String contents, User user) {
        if (!isOwner(user)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.POST_COMMENT_NOT_WRITER);
        }
        this.contents = contents;
    }
    public boolean isOwner(User user) {
        return this.user.equals(user);
    }
}
