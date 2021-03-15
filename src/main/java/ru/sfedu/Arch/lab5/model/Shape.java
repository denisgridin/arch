package ru.sfedu.Arch.lab5.model;

import java.util.Objects;

public class Shape extends Element {

    private Style style;

    private String text;

//    private Figure figure;

    public Shape() { }

    public Style getStyle() {
        return style;
    }

    public String getText() {
        return text;
    }



    public void setStyle(Style style) {
        this.style = style;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Figure getFigure() {
//        return figure;
//    }
//    public void setFigure(Figure figure) {
//        this.figure = figure;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Shape shape = (Shape) o;
        return o.toString().equals(shape.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), style, text);
    }

    @Override
    public String toString() {
        return "Shape{" +
                "style=" + style +
                ", text='" + text + '\'' +
//                ", figure=" + figure +
                ", id=" + getId() +
//                ", presentationId=" + getPresentationId() +
//                ", slideId=" + getSlideId() +
                ", name=" + getName() +
                ", layout=" + getLayout() +
                '}';
    }
}
