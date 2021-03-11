package ru.sfedu.Arch.providers;

import ru.sfedu.Arch.Result;


import java.util.UUID;

public interface IProvider {
    public <CommentClass> Result saveComment (CommentClass comment);
    public <CommentClass> Result getCommentById(Class<CommentClass> comment, UUID id);
    public <CommentClass> Result updateComment(CommentClass comment);
    public <CommentClass> Result deleteComment(CommentClass comment);
}
