package ru.sfedu.Arch.lab3.JoinedTable.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.JoinedTable.model.Comment;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class Api3Test {

    private final Api3 instance = new Api3();

    Result addComment () {
        Comment comment = new Comment();
        comment.setRole(Enums.Role.guest);
        comment.setDatetime(String.valueOf(new Date(System.currentTimeMillis())));
        comment.setText("Текст комментария");
        comment.setPresentationId(UUID.randomUUID());
        System.out.println(comment);

        return instance.saveComment(comment);
    }


    /**
     * TEST Save comment
     * Type: Success
     */
    @Test
    void saveCommentSuccess() {
        Result result = addComment();
        if (result.getStatus() == Enums.STATUS.success) {
            assertNotNull(result.getReturnValue());
        } else {
            fail();
        }
    }

    /**
     * TEST Save comment
     * Type: Fail
     */
    @Test
    void saveCommentFail() {
        Comment comment = new Comment();
        System.out.println(comment);

        Result result = instance.saveComment(comment);
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * TEST Get comment by id
     * Type: Success
     */
    @Test
    void getCommentByIdSuccess() {
        Api3 instance = new Api3();

        Result result = addComment();

        if (result.getStatus() == Enums.STATUS.success) {
            UUID id = (UUID) result.getReturnValue();

            Result resultGetComment = instance.getCommentById(Comment.class, id);

            if (resultGetComment.getStatus() == Enums.STATUS.success) {
                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
                System.out.println(optionalComment);
                assertTrue(optionalComment.isPresent());
            } else {
                fail();
            }
        } else {
            fail();
        }
    }


    /**
     * TEST Get comment by id
     * Type: Fail
     */
    @Test
    void getCommentByIdFail() {
        Result result = instance.getCommentById(Comment.class, UUID.randomUUID());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * TEST Update comment
     * Type: Success
     */
    @Test
    void updateComment() {
        Result result = addComment();
        if (result.getStatus() == Enums.STATUS.success) {
            UUID id = (UUID) result.getReturnValue();
            Result resultGetComment = instance.getCommentById(Comment.class, id);

            if (resultGetComment.getStatus() == Enums.STATUS.success) {
                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();

                if (optionalComment.isPresent()) {
                    Comment comment = optionalComment.get();

                    String text = "Edited text";
                    String datetime = String.valueOf(new Date(System.currentTimeMillis()));

                    comment.setText(text);
                    comment.setDatetime(datetime);
                    Result resultUpdateComment = instance.updateComment(comment);

                    assertTrue(resultUpdateComment.getStatus() == Enums.STATUS.success);


                    Result resultGetUpdatedComment = instance.getCommentById(Comment.class, id);

                    if (resultGetUpdatedComment.getStatus() == Enums.STATUS.success) {
                        Optional<Comment> optionalUpdatedComment = (Optional<Comment>) resultGetUpdatedComment.getReturnValue();

                        if (optionalUpdatedComment.isPresent()) {
                            Comment updatedComment = optionalUpdatedComment.get();

                            assertEquals(updatedComment.getDatetime(), datetime);
                            assertEquals(updatedComment.getText(), text);
                        } else {
                            fail();
                        }
                    } else {
                        fail();
                    }

                } else {
                    fail();
                }
            } else {
                fail();
            }
        }
    }

    /**
     * TEST Update comment
     * Type: Fail
     */
    @Test
    void updateCommentFail () {
        Result result = instance.updateComment(new Comment());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }


    /**
     * TEST Delete comment
     * Type: Success
     */
    @Test
    void deleteComment() {
        Result result = addComment();

        if (result.getStatus() == Enums.STATUS.success) {
            UUID id = (UUID) result.getReturnValue();

            if (id != null) {
                Api3 instance = new Api3();

                Result resultGetComment = instance.getCommentById(Comment.class, id);

                if (resultGetComment.getStatus() == Enums.STATUS.success) {
                    Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
                    if (optionalComment.isPresent()) {
                        Comment comment = optionalComment.get();
                        Result resultDeleteComment = instance.deleteComment(comment);

                        assertTrue(resultDeleteComment.getStatus() == Enums.STATUS.success);

                        Result resultGetRemovedComment = instance.getCommentById(Comment.class, id);
                        assertTrue(resultGetRemovedComment.getStatus() == Enums.STATUS.error);
                    } else {
                        fail();
                    }
                } else {
                    fail();
                }
            } else {
                fail();
            }
        } else {
            fail();
        }
    }


    /**
     * TEST Delete comment
     * Type: Fail
     */
    @Test
    void deleteCommentFail () {
        Result result = instance.deleteComment(new Comment());
        assertTrue(result.getStatus() == Enums.STATUS.error);
    }
}