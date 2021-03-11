package ru.sfedu.Arch.providers;

import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.SingleTable.model.Comment;

import java.util.UUID;

public interface ISingleTableProvider {
    public Result saveComment (Comment comment);
    public Result getCommentById(Class<Comment> comment, UUID id);
    public Result updateComment(Comment comment);
    public Result deleteComment(Comment comment);
}
