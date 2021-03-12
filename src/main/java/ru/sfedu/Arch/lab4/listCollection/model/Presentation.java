package ru.sfedu.Arch.lab4.listCollection.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity(name = Constants.ENTITY_PRESENTATION_LIST_COLLECTION)
@Table(name = Constants.TABLE_PRESENTATION_LIST_COLLECTION)
public class Presentation implements Serializable {
    public Presentation () {}
    public static Logger log = LogManager.getLogger(Presentation.class);

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = Constants.FIELD_NAME)
    private String name;

    @Column(name = Constants.FIELD_FILL_COLOR)
    private String fillColor;

    @Column(name = Constants.FIELD_FONT_FAMILY)
    private String fontFamily;

    @ElementCollection
    @CollectionTable(
            name = Constants.TABLE_SLIDES_LIST_COLLECTION,
            joinColumns = @JoinColumn(name = Constants.FIELD_PRESENTATION_ID)
    )
    @OrderColumn
    @Column(name = Constants.FIELD_PRESENTATION_ID)
    private List<Slide> slides = new ArrayList<Slide>();

//    private ArrayList<Comment> comments;
//    private HashMap marks;

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

    public List<Slide> getSlides () {
        return slides;
    }

    public void setSlides (List<Slide> items) {
        slides = items;
    }

//    public ArrayList<Slide> getSlides() {
//        return slides;
//    }
//
//    public void setSlides(ArrayList slides) {
//        this.slides = slides;
//    }
//
//    public ArrayList<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(ArrayList comments) {
//        this.comments = comments;
//    }
//
//    public HashMap getMarks() {
//        return marks;
//    }
//
//    public void setMarks(HashMap marks) {
//        this.marks = marks;
//    }

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
                '}';
        return formatReturn;
    }
}
