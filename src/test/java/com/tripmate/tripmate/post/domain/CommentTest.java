package com.tripmate.tripmate.post.domain;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CommentTest {

    @Test
    @DisplayName("댓글 내용을 수정한다.")
    void test1() {
        User user = createUser("test1");
        Comment comment = createComment(user);

        String updateContents = "update contents";
        comment.updateContents(updateContents, user);

        Assertions.assertThat(comment.getContents()).isEqualTo(updateContents);
    }

    @Test
    @DisplayName("작성자가 아니면 댓글 내용을 수정하지 못한다.")
    void test2() {
        User user = createUser("test1");
        Comment comment = createComment(user);

        User user2 = createUser("test2");
        String updateContents = "update contents";

        Assertions.assertThatThrownBy(() -> comment.updateContents(updateContents, user2))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ResultCode.POST_COMMENT_NOT_WRITER.getMessage());
    }

    @Test
    @DisplayName("대댓글 생성시 댓글에 반영된다.")
    void test3() {
        User user = createUser("test1");
        User user2 = createUser("test2");
        Comment comment = createComment(user);
        Comment comment2 = createComment(user2);

        comment.addCommentReply(comment2);

        Assertions.assertThat(comment.getChildComments()).contains(comment2);
        Assertions.assertThat(comment2.getParentComment()).isEqualTo(comment);

    }

    private User createUser(String name) {
        return User.builder()
                .name(name)
                .build();
    }

    private Comment createComment(User user) {
        return Comment.builder()
                .contents("test contents")
                .user(user)
                .childComments(new ArrayList<>())
                .build();
    }
}
