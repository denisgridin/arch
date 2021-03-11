//package ru.sfedu.Arch.lab3.JoinedTable.api;
//
//import org.junit.jupiter.api.Test;
//import ru.sfedu.Arch.Enums;
//import ru.sfedu.Arch.Result;
//import ru.sfedu.Arch.lab3.JoinedTable.model.Comment;
//
//import java.sql.Date;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JoinedTableApiTest {
//
//
//    Result addComment () {
//        JoinedTableApi instance = new JoinedTableApi();
//
//        Comment comment = new Comment();
//        comment.setRole(Enums.Role.editor);
//        comment.setDatetime(String.valueOf(new Date(System.currentTimeMillis())));
//        comment.setText("test text");
//        comment.setPresentationId(UUID.randomUUID());
//        System.out.println(comment);
//
//        return instance.saveComment(comment);
//    }
//
//    @Test
//    void saveCommentSuccess() {
//        Result result = addComment();
//        if (result.getStatus() == Enums.STATUS.success) {
//            assertNotNull(result.getReturnValue());
//        } else {
//            fail();
//        }
//    }
//
//
//    @Test
//    void getCommentByIdSuccess() {
//        JoinedTableApi instance = new JoinedTableApi();
//
//        Result result = addComment();
//
//        if (result.getStatus() == Enums.STATUS.success) {
//            UUID id = (UUID) result.getReturnValue();
//
//            Result resultGetComment = instance.getCommentById(Comment.class, id);
//
//            if (resultGetComment.getStatus() == Enums.STATUS.success) {
//                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
//                System.out.println(optionalComment);
//                assertTrue(optionalComment.isPresent());
//            } else {
//                fail();
//            }
//        } else {
//            fail();
//        }
//    }
//
//    @Test
//    void updateComment() {
//        JoinedTableApi instance = new JoinedTableApi();
//
//        Result result = addComment();
//
//        if (result.getStatus() == Enums.STATUS.success) {
//            UUID id = (UUID) result.getReturnValue();
//
//            Result resultGetComment = instance.getCommentById(Comment.class, id);
//
//            if (resultGetComment.getStatus() == Enums.STATUS.success) {
//                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
//
//                if (optionalComment.isPresent()) {
//                    Comment comment = optionalComment.get();
//
//                    String text = "Edited text";
//                    String datetime = String.valueOf(new Date(System.currentTimeMillis()));
//
//                    comment.setText(text);
//                    comment.setDatetime(datetime);
//                    Result resultUpdateComment = instance.updateComment(comment);
//
//                    assertTrue(resultUpdateComment.getStatus() == Enums.STATUS.success);
//
//
//                    Result resultGetUpdatedComment = instance.getCommentById(Comment.class, id);
//
//                    if (resultGetUpdatedComment.getStatus() == Enums.STATUS.success) {
//                        Optional<Comment> optionalUpdatedComment = (Optional<Comment>) resultGetUpdatedComment.getReturnValue();
//
//                        if (optionalUpdatedComment.isPresent()) {
//                            Comment updatedComment = optionalUpdatedComment.get();
//
//                            assertEquals(updatedComment.getDatetime(), datetime);
//                            assertEquals(updatedComment.getText(), text);
//                        } else {
//                            fail();
//                        }
//                    } else {
//                        fail();
//                    }
//
//                } else {
//                    fail();
//                }
//            } else {
//                fail();
//            }
//        }
//    }
//
//    @Test
//    void deleteComment() {
//        Result result = addComment();
//
//        if (result.getStatus() == Enums.STATUS.success) {
//            UUID id = (UUID) result.getReturnValue();
//
//            if (id != null) {
//                JoinedTableApi instance = new JoinedTableApi();
//
//                Result resultGetComment = instance.getCommentById(Comment.class, id);
//
//                if (resultGetComment.getStatus() == Enums.STATUS.success) {
//                    Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
//                    if (optionalComment.isPresent()) {
//                        Comment comment = optionalComment.get();
//                        Result resultDeleteComment = instance.deleteComment(comment);
//
//                        assertTrue(resultDeleteComment.getStatus() == Enums.STATUS.success);
//
//                        Result resultGetRemovedComment = instance.getCommentById(Comment.class, id);
//                        assertTrue(resultGetRemovedComment.getStatus() == Enums.STATUS.error);
//                    } else {
//                        fail();
//                    }
//                } else {
//                    fail();
//                }
//            } else {
//                fail();
//            }
//        } else {
//            fail();
//        }
//    }
//}