package ru.sfedu.Arch.lab4.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.*;


public class Presentation implements Serializable {
    public Presentation () {}
    public static Logger log = LogManager.getLogger(Presentation.class);

    private UUID id;
    private String name;
    private String fillColor;
    private String fontFamily;
    private ArrayList<Slide> slides;
    private ArrayList<Comment> comments;
    private HashMap marks;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public ArrayList<Slide> getSlides() {
        return slides;
    }

    public void setSlides(ArrayList slides) {
        this.slides = slides;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList comments) {
        this.comments = comments;
    }

    public HashMap getMarks() {
        return marks;
    }

    public void setMarks(HashMap marks) {
        this.marks = marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presentation that = (Presentation) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(fillColor, that.fillColor) &&
                Objects.equals(fontFamily, that.fontFamily);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fillColor, fontFamily);
    }

    @Override
    public String toString() {
        String formatReturn = "Presentation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fillColor='" + fillColor + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", slides=" + getSlides()  +
                ", comments=" + getComments() +
                ", marks=" + getMarks() +
                '}';
        return formatReturn;
    }
}
