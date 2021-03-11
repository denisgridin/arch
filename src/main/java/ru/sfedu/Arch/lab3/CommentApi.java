//package ru.sfedu.Arch.lab3;
//
//import ru.sfedu.Arch.Constants;
//import ru.sfedu.Arch.Enums;
//import ru.sfedu.Arch.Result;
//import ru.sfedu.Arch.lab3.JoinedTable.model.Comment;
//import ru.sfedu.Arch.providers.IProvider;
//import ru.sfedu.Arch.utils.Messages;
//
//import java.sql.Date;
//import java.util.HashMap;
//import java.util.Optional;
//import java.util.UUID;
//
//public class CommentApi {
//
//    public <T extends IProvider, P extends IComment> Result buildAndSaveComment(HashMap args, T instance, P comment) {
//        try {
//            comment.setRole(Enums.Role.valueOf((String) args.get(Constants.FIELD_ROLE)));
//            comment.setDatetime(String.valueOf(new Date(System.currentTimeMillis())));
//            comment.setText((String) args.get(Constants.FIELD_TEXT));
//            comment.setPresentationId(UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID)));
//
//            return instance.saveComment(comment);
//        } catch (Exception error) {
//            return new Result(Enums.STATUS.error, error);
//        }
//    }
//
//    public Result buildAndUpdateComment(HashMap args) {
//        try {
//            Result resultGetComment = getCommentById(Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
//            if (resultGetComment.getStatus() == Enums.STATUS.success) {
//                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
//                Comment comment = optionalComment.get();
//
//                comment.setPresentationId(UUID.fromString(String.valueOf(args.getOrDefault(Constants.FIELD_PRESENTATION_ID, comment.getPresentationId()))));
//                comment.setRole(Enums.Role.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_ROLE, comment.getRole()))));
//                comment.setText((String) args.getOrDefault(Constants.FIELD_TEXT, comment.getText()));
//                return updateComment(comment);
//            } else {
//                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
//            }
//
//        } catch (Exception error) {
//            event.error(1, error);
//            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
//        }
//    }
//
//    public Result buildAndDeleteComment(HashMap args) {
//        try {
//            Result resultGetComment = getCommentById(Comment.class, UUID.fromString((String) args.get(Constants.FIELD_ID)));
//            if (resultGetComment.getStatus() == Enums.STATUS.success) {
//                Optional<Comment> optionalComment = (Optional<Comment>) resultGetComment.getReturnValue();
//                Comment comment = optionalComment.get();
//                return deleteComment(comment);
//            } else {
//                return new Result(Enums.STATUS.error, Messages.ERROR_GET_BEAN);
//            }
//        } catch (Exception error) {
//            event.error(1, error);
//            event.error(2, Messages.ERROR_REMOVE_BEAN);
//            return new Result(Enums.STATUS.error, Messages.ERROR_REMOVE_BEAN);
//        }
//    }
//}
