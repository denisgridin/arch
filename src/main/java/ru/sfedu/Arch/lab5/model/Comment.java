package ru.sfedu.Arch.lab5.model;

import ru.sfedu.Arch.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


@Entity(name = Constants.ENTITY_COMMENT_MAPPED_SUPER_CLASS)
@Table(name = Constants.TABLE_COMMENTS)
public class Comment extends Feedback implements Serializable {

    public Comment () {}

    @Column(name = Constants.FIELD_TEXT, nullable = false)
    private String text;

    @Column(name = Constants.FIELD_DATETIME, nullable = false)
    private String datetime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDatetime () {
        return datetime;
    }

    public void setDatetime (String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", datetime='" + datetime + '\'' +
                ", id=" + getId() +
                ", presentationId=" + getPresentationId() +
                ", role=" + getRole() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text) &&
                Objects.equals(datetime, comment.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, datetime);
    }
}

