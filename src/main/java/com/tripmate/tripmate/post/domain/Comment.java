package com.tripmate.tripmate.post.domain;


import com.tripmate.tripmate.common.BaseTimeEntity;
import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
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

    private int likeCount = 0;

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

    public void increaseLike() {
        this.likeCount++;
    }

    public void reduceLike() {
        this.likeCount--;
    }
}
