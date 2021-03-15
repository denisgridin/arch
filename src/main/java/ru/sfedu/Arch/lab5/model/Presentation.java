package ru.sfedu.Arch.lab5.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ru.sfedu.Arch.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity(name = Constants.PRESENTATION_MAIN)
@Table(name = Constants.TABLE_PRESENTATION_MAIN)
public class Presentation implements Serializable {
    public Presentation () {}
    public static Logger log = LogManager.getLogger(Presentation.class);

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = Constants.FIELD_ID, columnDefinition="uniqueidentifier")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = Constants.FIELD_NAME, nullable = false)
    private String name;

    @Column(name = Constants.FIELD_FILL_COLOR, nullable = false)
    private String fillColor;

    @Column(name = Constants.FIELD_FONT_FAMILY, nullable = false)
    private String fontFamily;

    @OneToMany(mappedBy = Constants.FIELD_PRESENTATION, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Slide> slides = new ArrayList<Slide>();

    @OneToMany(mappedBy = Constants.FIELD_PRESENTATION, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<Comment>();;
//
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

    public void setComments (List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments () {
        return comments;
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
                ", comments=" + getComments()  +
                '}';
        return formatReturn;
    }
}
