package ru.sfedu.Arch.lab5.model;

import java.io.Serializable;

public class Content extends Element implements Serializable {

    private Font font;

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
