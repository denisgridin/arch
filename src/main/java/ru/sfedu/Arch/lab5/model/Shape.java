package ru.sfedu.Arch.lab5.model;


import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;

import javax.persistence.*;
import java.util.Objects;


@Entity(name = Constants.ENTITY_SHAPE_MAIN)
@Table(name = Constants.TABLE_SHAPE_MAIN)
public class Shape extends Element {


    @OneToOne(mappedBy = "shape", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Style style;

    @Column(name = Constants.FIELD_TEXT, nullable = false)
    private String text;

    private Enums.Figure figure;

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

    public Enums.Figure getFigure() {
        return figure;
    }
    public void setFigure(Enums.Figure figure) {
        this.figure = figure;
    }

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
                ", figure=" + figure +
                ", id=" + getId() +
                ", slideId=" + getSlide().getId() +
                ", name=" + getName() +
                ", layout=" + getLayout() +
                '}';
    }
}
