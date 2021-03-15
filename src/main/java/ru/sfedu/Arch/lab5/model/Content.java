package ru.sfedu.Arch.lab5.model;

import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;



@Entity(name = Constants.ENTITY_CONTENT_MAIN)
@Table(name = Constants.TABLE_CONTENT_MAIN)
public class Content extends Element implements Serializable {


    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Font font;


    @Column(name = Constants.FIELD_TEXT, nullable = false)
    private String text;

    public Content () {}

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Content{" +
                "font=" + font +
                ", text='" + text + '\'' +
                ", name='" + getName() + '\'' +
                ", id=" + getId() +
//                ", presentationId=" + getPresentationId() +
//                ", slideId=" + getSlideId() +
                ", layout=" + getLayout() +
                '}';
    }
}
