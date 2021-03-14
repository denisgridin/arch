package ru.sfedu.Arch.providers;

import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.lab3.JoinedTable.model.Assessment;
import ru.sfedu.Arch.lab3.JoinedTable.model.Comment;

import java.util.UUID;

public interface IJoinedTableProvider {
    public Result saveComment (Comment comment);
    public Result getCommentById(Class<Comment> comment, UUID id);
    public Result updateComment(Comment comment);
    public Result deleteComment(Comment comment);

    public Result saveAssessment(Assessment assessment);
    public Result getAssessmentById(Class<Assessment> assessment, UUID id);
    public Result updateAssessment(Assessment assessment);
    public Result deleteAssessment(Assessment assessment);
}
